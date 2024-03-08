import { useEffect, useState } from "react";
import topicService from "../../services/topic.service";
import { Container, SimpleGrid } from "@mantine/core";
import { TopicCard } from "./TopicCard";
import { TopicCardInfo } from "../../types/types";
import { RootState, useAppSelector } from "../../redux";

export function Topics() {
  const [topics, setTopics] = useState<TopicCardInfo[] | undefined>(undefined);
  const user: any = useAppSelector((state: RootState) => state.user.username);

  useEffect(() => {
    topicService
      .getAll()
      .then((res: any) => {
        setTopics(res);
      })
      .catch((err: any) => {
        console.log(err);
      });
  }, [user]);

  return (
    <Container size="100%" h="100vh" p="5%">
      <SimpleGrid cols={{ base: 1, sm: 2 }} spacing="lg" verticalSpacing="lg">
        {topics &&
          topics.map((t: TopicCardInfo) => (
            <TopicCard key={t.id} id={t.id} name={t.name} image={t.image} />
          ))}
      </SimpleGrid>
    </Container>
  );
}
