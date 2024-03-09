import { Button, Flex, Textarea } from "@mantine/core";
import { useForm, UseFormReturnType } from "@mantine/form";
import topicService from "../../services/topic.service";
import { notifications } from "@mantine/notifications";
import commentService from "../../services/comment.service";

interface Props {
  idTopic: number;
  content?: string;
  idComment?: number;
  reload?: any;
  editing?: boolean;
  setEditing?: any;
}

export function CommentInput({
  idTopic,
  content,
  idComment,
  reload,
  editing,
  setEditing,
}: Props) {
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
    if (editing && idComment) {
      commentService
        .updateCommentContent(idComment, form.values.content)
        .then(() => {
          const val = editing;
          setEditing(!val);

          reload();
          notifications.show({
            title: "Edit Successful",
            message: "You have edited a comment",
          });
        })
        .catch((err: any) =>
          notifications.show({
            title: "Editing Failed",
            message: err.response.message,
          })
        );
    } else {
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
    }
  };

  return (
    <form onSubmit={form.onSubmit(() => submitComment())}>
      <Textarea
        size="sm"
        radius="md"
        label={editing ? "Edit comment" : "Leave a comment"}
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
        <Button type="submit">{editing ? "Edit" : "Comment"}</Button>
      </Flex>
    </form>
  );
}
