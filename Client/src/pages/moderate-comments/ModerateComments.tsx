import { useEffect, useState } from "react";
import commentService from "../../services/comment.service";
import permissionService from "../../services/permission.service";
import { Flex } from "@mantine/core";
import { PendingComment } from "./PendingComment";
import { RootState, useAppSelector } from "../../redux";

export function ModerateComments() {
  const [comments, setComments] = useState<any>();
  const [permissions, setPermissions] = useState<any>();
  const user: any = useAppSelector((state: RootState) => state.user);

  useEffect(() => {
    reload();
  }, [user]);

  const reload = () => {
    commentService
      .getAllPending()
      .then((res: any) => {
        setComments(res);
      })
      .catch((err: any) => console.log(err));

    permissionService
      .getMy()
      .then((res: any) => {
        setPermissions(res);
      })
      .catch((err: any) => console.log(err));
  };

  return (
    <Flex
      align="center"
      justify="flex-start"
      mt="lg"
      direction="column"
      gap="md"
    >
      {comments &&
        permissions &&
        comments.map((c: any) => (
          <PendingComment
            key={c.id}
            id={c.id}
            content={c.content}
            timestamp={c.timestamp}
            topic={c.topic}
            username={c.user.username}
            permissions={permissions.find((p: any) => p.idTopic === c.topic.id)}
            reload={reload}
          />
        ))}
    </Flex>
  );
}
