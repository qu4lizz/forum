import { Checkbox, Flex } from "@mantine/core";
import { UpdateUserRequest } from "../../types/types";
import { useForceUpdate } from "@mantine/hooks";

interface Props {
  selected: string;
  topics: any;
  user: UpdateUserRequest;
}

export function PermissionCheckbox({ selected, topics, user }: Props) {
  const topic = topics.find((t: any) => t.name === selected);
  const topicPermissions = user.permissions.find(
    (p: any) => p.idTopic === topic.id
  );

  const forceUpdate = useForceUpdate();

  return (
    <>
      {topicPermissions && (
        <Flex direction="column" gap="md" justify="center" align="flex-start">
          <Checkbox
            label="Write"
            checked={topicPermissions.write}
            onChange={(event) => {
              if (topicPermissions)
                topicPermissions.write = event.currentTarget.checked;
              forceUpdate();
            }}
          />
          <Checkbox
            label="Update"
            checked={topicPermissions.update}
            onChange={(event) => {
              if (topicPermissions)
                topicPermissions.update = event.currentTarget.checked;
              forceUpdate();
            }}
          />
          <Checkbox
            label="Delete"
            checked={topicPermissions.delete}
            onChange={(event) => {
              if (topicPermissions)
                topicPermissions.delete = event.currentTarget.checked;
              forceUpdate();
            }}
          />
        </Flex>
      )}
    </>
  );
}
