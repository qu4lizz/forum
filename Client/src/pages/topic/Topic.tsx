import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import topicService from "../../services/topic.service";
import { TopicInfo } from "../../types/types";
import { Card, Flex, Group, Image, Text } from "@mantine/core";
import { getImageSrc } from "../../utils/utilFunctions";
import classes from "../home/TopicCard.module.css";
import { Comment } from "./Comment";

export function Topic() {
  const [topic, setTopic] = useState<TopicInfo | undefined>();

  const { id } = useParams();

  useEffect(() => {
    id &&
      topicService
        .getById(id)
        .then((res: TopicInfo) => {
          setTopic(res);
        })
        .catch((err: any) => {
          console.log(err);
        });
  }, [id]);

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
            {topic.comments.map((c: any) => (
              <Comment
                content={c.content}
                timestamp={c.timestamp}
                username={c.user.username}
              />
            ))}
          </Flex>
        </>
      )}
    </>
  );
}
