import { Card, Group, Text, Image } from "@mantine/core";
import classes from "./TopicCard.module.css";
import { getImageSrc } from "../../utils/utilFunctions";
import { useNavigate } from "react-router-dom";

interface Props {
  id: number;
  name: string;
  image: any;
}

export function TopicCard({ id, name, image }: Props) {
  const navigate = useNavigate();

  return (
    <Card
      withBorder
      radius="md"
      p="md"
      className={classes.card}
      onClick={() => navigate(`/topics/${id}`)}
    >
      <Card.Section>
        <Image src={getImageSrc(image)} alt={name} height={320} />
      </Card.Section>

      <Card.Section className={classes.section} mt="md">
        <Group justify="center">
          <Text fz="lg" fw={500}>
            {name}
          </Text>
        </Group>
      </Card.Section>
    </Card>
  );
}
