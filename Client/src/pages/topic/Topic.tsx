import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import topicService from "../../services/topic.service";
import { TopicInfo } from "../../types/types";
import { Card, Flex, Group, Image, Text } from "@mantine/core";
import { getImageSrc } from "../../utils/utilFunctions";
import classes from "../home/TopicCard.module.css";
import { Comment } from "./Comment";
import { RootState, useAppSelector } from "../../redux";
import { CommentInput } from "./CommentInput";

export function Topic() {
  const [topic, setTopic] = useState<TopicInfo | undefined>();
  const [permissions, setPermissions] = useState<any>();
  const username: any = useAppSelector(
    (state: RootState) => state.user.username
  );

  const { id } = useParams();

  useEffect(() => {
    if (id) {
      topicService
        .getById(id)
        .then((res: TopicInfo) => {
          setTopic(res);
        })
        .catch((err: any) => {
          console.log(err);
        });

      topicService
        .getPermissionsById(id)
        .then((res: any) => {
          setPermissions(res);
        })
        .catch((err: any) => {
          console.log(err);
        });
    }
  }, [id, username]);

  return (
    <>
      {topic && (
        <>
          <Card withBorder radius="md" p="md" m="lg" className={classes.card}>
            <Card.Section>
              <Image
                src={getImageSrc(topic.image)}
                alt={topic.name}
                height={320}
              />
            </Card.Section>

            <Card.Section className={classes.section} mt="md">
              <Group justify="center">
                <Text fz="lg" fw={500}>
                  {topic.name}
                </Text>
              </Group>
            </Card.Section>
          </Card>
          <Flex gap="md" direction="column" justify="center" align="center">
            {topic.comments.length === 0 && (
              <Text size="lg" mt="lg">
                No comments on this topic
              </Text>
            )}
            {permissions.write && <CommentInput idTopic={topic.id} />}

            {topic.comments.map((c: any) => (
              <Comment
                key={c.id}
                currentUser={username}
                content={c.content}
                timestamp={c.timestamp}
                username={c.user.username}
                permissions={permissions}
              />
            ))}
          </Flex>
        </>
      )}
    </>
  );
}
