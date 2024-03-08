import { Button, Paper, Flex, Textarea } from "@mantine/core";
import { useForm, UseFormReturnType } from "@mantine/form";
import classes from "./Comment.module.css";
import topicService from "../../services/topic.service";
import { notifications } from "@mantine/notifications";

interface Props {
  idTopic: number;
  content?: string;
}

export function CommentInput({ idTopic, content }: Props) {
  const form: UseFormReturnType<any> = useForm({
    initialValues: {
      content: content ? content : "",
    },

    validate: {
      content: (value: string) =>
        value.trim().length > 0 && form.isDirty()
          ? null
          : "Comment cannot be empty",
    },
  });

  const submitComment = () => {
    const obj = {
      content: form.values.content,
      timestamp: new Date(),
      idTopic,
    };

    topicService
      .submitCommentToTopic(obj)
      .then(() => {
        notifications.show({
          title: "Successfully Commented",
          message: "Your comment was sent. Wait until it's approved.",
        });
        form.reset();
      })
      .catch((err: any) => {
        notifications.show({
          title: "Comment Failed",
          message: err.response.data,
        });
      });
  };

  return (
    <Paper w="90%" mx="5%" withBorder radius="md" className={classes.comment}>
      <form onSubmit={form.onSubmit(() => submitComment())}>
        <Textarea
          size="sm"
          radius="md"
          label="Leave a comment"
          placeholder="Type here..."
          value={form.values.content}
          onChange={(target) =>
            form.setFieldValue("content", target.currentTarget.value)
          }
          autosize
          error={form.errors.content}
          minRows={3}
        />
        <Flex justify="flex-end" mt="xs">
          <Button type="submit">Comment</Button>
        </Flex>
      </form>
    </Paper>
  );
}
