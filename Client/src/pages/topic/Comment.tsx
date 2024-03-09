import { Text, Paper, Divider, ActionIcon, rem, Flex } from "@mantine/core";
import classes from "./Comment.module.css";
import { Edit, Trash } from "tabler-icons-react";

interface Props {
  currentUser: string;
  username: string;
  content: string;
  timestamp: string;
  permissions: any;
}

export function Comment({
  currentUser,
  username,
  content,
  timestamp,
  permissions,
}: Props) {
  const onEditClick = () => {};

  const onDeleteClick = () => {};

  return (
    <Paper withBorder radius="md" className={classes.comment}>
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
              <Edit style={{ width: rem(24), height: rem(24) }} />
            </ActionIcon>
          )}
          {permissions.delete && (
            <ActionIcon size={36}>
              <Trash
                style={{ width: rem(24), height: rem(24) }}
                onClick={onDeleteClick}
              />
            </ActionIcon>
          )}
        </span>
      </Flex>
      <Divider size="sm" my="sm" />
      <Text size="sm">{content}</Text>
    </Paper>
  );
}
