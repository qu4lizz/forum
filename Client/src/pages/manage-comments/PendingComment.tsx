import { ActionIcon, Divider, Flex, Paper, Text, rem } from "@mantine/core";
import { Check, Edit, EditOff, Trash } from "tabler-icons-react";
import commentService from "../../services/comment.service";

import classes from "../topic/Comment.module.css";
import { notifications } from "@mantine/notifications";
import { modals } from "@mantine/modals";
import { CommentInput } from "../topic/CommentInput";
import { useState } from "react";

interface Props {
  id: number;
  username: string;
  content: string;
  timestamp: string;
  permissions: any;
  topic: any;
  reload: any;
}

export function PendingComment({
  id,
  content,
  permissions,
  timestamp,
  username,
  topic,
  reload,
}: Props) {
  const [editing, setEditing] = useState<boolean>(false);

  const onApproveClick = () => {
    commentService
      .updateCommentStatus(id, { status: "APPROVED" })
      .then(() => {
        notifications.show({
          title: "Successfully Approved",
          message: `Comment was approved, it will show under ${topic.name}`,
        });

        reload();
      })
      .catch((err: any) => {
        notifications.show({
          title: "Approval Failed",
          message: err.response.data,
        });
      });
  };

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
          message: "Comment was rejected",
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
      title: "Are you sure you want to reject this comment?",
      centered: true,
      children: (
        <Text size="sm">
          After you reject this comment it won't be seen anywhere
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
          <Text fz="md">{topic.name}</Text>
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
          {permissions.write && (
            <ActionIcon
              mr={permissions.delete || permissions.update ? 15 : 0}
              size={36}
              onClick={onApproveClick}
            >
              <Check style={{ width: rem(24), height: rem(24) }} />
            </ActionIcon>
          )}
          {permissions.update && (
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
          idTopic={topic.id}
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
