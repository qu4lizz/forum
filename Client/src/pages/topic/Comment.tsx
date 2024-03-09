import { Text, Paper, Divider, ActionIcon, rem, Flex } from "@mantine/core";
import classes from "./Comment.module.css";
import { Edit, EditOff, Trash } from "tabler-icons-react";
import { useState } from "react";
import commentService from "../../services/comment.service";
import { notifications } from "@mantine/notifications";
import { modals } from "@mantine/modals";
import { CommentInput } from "./CommentInput";

interface Props {
  currentUser: string;
  username: string;
  id: number;
  content: string;
  timestamp: string;
  permissions: any;
  reload: any;
  topicId: number;
}

export function Comment({
  currentUser,
  username,
  id,
  content,
  timestamp,
  permissions,
  reload,
  topicId,
}: Props) {
  const [editing, setEditing] = useState<boolean>(false);

  const onEditClick = () => {
    const val = editing;
    setEditing(!val);
  };

  const onDeleteClick = () => {
    commentService
      .updateCommentStatus(id, { status: "REJECTED" })
      .then(() => {
        notifications.show({
          title: "Successful Deletion",
          message: "Comment was deleted",
        });

        reload();
      })
      .catch((err: any) => {
        notifications.show({
          title: "Deletion Failed",
          message: err.response.data,
        });
      });
  };

  const openDeleteModal = () => {
    modals.openConfirmModal({
      title: "Are you sure you want to delete this comment?",
      centered: true,
      children: (
        <Text size="sm">
          After you delete this comment it won't be seen anywhere
        </Text>
      ),
      labels: { confirm: "Delete", cancel: "Cancel" },
      confirmProps: { color: "red" },
      onConfirm: () => onDeleteClick(),
    });
  };

  return (
    <Paper withBorder radius="md" className={classes.comment} w="90%">
      <Flex justify="space-between">
        <div>
          <Text fz="sm">{username}</Text>
          <Text fz="xs" c="dimmed">
            {new Date(timestamp).toLocaleString("default", {
              year: "numeric",
              month: "numeric",
              day: "numeric",
              hour: "numeric",
              minute: "numeric",
            })}
          </Text>
        </div>
        <span>
          {(permissions.update || currentUser === username) && (
            <ActionIcon
              mr={permissions.delete ? 15 : 0}
              size={36}
              onClick={onEditClick}
            >
              {!editing ? (
                <Edit style={{ width: rem(24), height: rem(24) }} />
              ) : (
                <EditOff style={{ width: rem(24), height: rem(24) }} />
              )}
            </ActionIcon>
          )}
          {permissions.delete && (
            <ActionIcon size={36} onClick={openDeleteModal}>
              <Trash style={{ width: rem(24), height: rem(24) }} />
            </ActionIcon>
          )}
        </span>
      </Flex>
      <Divider size="sm" my="sm" />
      {!editing ? (
        <Text size="sm">{content}</Text>
      ) : (
        <CommentInput
          idTopic={topicId}
          content={content}
          idComment={id}
          reload={reload}
          editing={editing}
          setEditing={setEditing}
        />
      )}
    </Paper>
  );
}
