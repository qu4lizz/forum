import { Checkbox, Flex } from "@mantine/core";
import { UpdateUserRequest } from "../../types/types";
import { useEffect, useState } from "react";

interface Props {
  selected: string;
  topics: any;
  user: UpdateUserRequest;
}

export function PermissionCheckbox({ selected, topics, user }: Props) {
  const [topicPermissions, setTopicPermissions] = useState<any>();

  useEffect(() => {
    const topic = topics.find((t: any) => t.name === selected);
    setTopicPermissions(
      user.permissions.find((p: any) => p.idTopic === topic.id)
    );
    console.log("das");
  }, [selected]);

  return (
    <>
      {topicPermissions && (
        <Flex direction="column" gap="md" justify="center" align="flex-start">
          <Checkbox
            key={topicPermissions}
            label="Write"
            checked={topicPermissions.write}
            onChange={(event) => {
              if (topicPermissions)
                setTopicPermissions((prevTopicPermissions: any) => ({
                  ...prevTopicPermissions,
                  delete: event.currentTarget.checked,
                }));
            }}
          />
          <Checkbox
            key={topicPermissions}
            label="Update"
            checked={topicPermissions.update}
            onChange={(event) => {
              if (topicPermissions)
                setTopicPermissions((prevTopicPermissions: any) => ({
                  ...prevTopicPermissions,
                  update: event.currentTarget.checked,
                }));
            }}
          />
          <Checkbox
            key={topicPermissions}
            label="Delete"
            checked={topicPermissions.delete}
            onChange={(event) => {
              if (topicPermissions)
                setTopicPermissions((prevTopicPermissions: any) => ({
                  ...prevTopicPermissions,
                  delete: event.currentTarget.checked,
                }));
            }}
          />
        </Flex>
      )}
    </>
  );
}
