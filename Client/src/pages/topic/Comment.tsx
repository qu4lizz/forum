import { Text, Group, Paper } from "@mantine/core";
import classes from "./Comment.module.css";

interface Props {
  username: string;
  content: string;
  timestamp: string;
}

export function Comment({ username, content, timestamp }: Props) {
  return (
    <Paper withBorder radius="md" className={classes.comment}>
      <Group>
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
      </Group>
      <Text pl={54} pt="sm" size="sm">
        {content}
      </Text>
    </Paper>
  );
}
