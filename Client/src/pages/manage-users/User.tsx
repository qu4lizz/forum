import {
  Text,
  Badge,
  Group,
  Table,
  ActionIcon,
  rem,
  Flex,
} from "@mantine/core";
import { modals } from "@mantine/modals";
import { IconPencil } from "@tabler/icons-react";
import { ComboBox } from "./ComboBox";
import { useState } from "react";
import { UpdateUserRequest } from "../../types/types";
import { ComboBoxTopic } from "./ComboBoxTopic";
import userService from "../../services/user.service";
import { notifications } from "@mantine/notifications";
import { PermissionCheckbox } from "./PermissionsCheckbox";

interface Props {
  user: any;
  topics: any;
  reload: any;
}

const statusColors: Record<string, string> = {
  REQUESTED: "orange",
  APPROVED: "green",
  REJECTED: "red",
};

export function User({ user, topics, reload }: Props) {
  const updatedUser: UpdateUserRequest = JSON.parse(JSON.stringify(user));
  const [selectedTopic, setSelectedTopic] = useState<string>(topics[0].name);

  const openModal = () =>
    modals.openConfirmModal({
      title: `Edit user '${user.username}'`,
      children: (
        <Flex direction="column" align="center" gap="xl">
          <Flex direction="row" align="center" justify="space-evenly" w="100%">
            <Flex direction="column" align="center">
              Role
              <ComboBox
                selected={user.role}
                type="ROLE"
                updatedUser={updatedUser}
              />
            </Flex>
            <Flex direction="column" align="center">
              Status
              <ComboBox
                selected={user.status}
                type="STATUS"
                updatedUser={updatedUser}
              />
            </Flex>
          </Flex>
          <Flex direction="column" align="center" gap="lg">
            <Flex direction="column" align="center">
              Permissions for Topic
              <ComboBoxTopic
                selected={selectedTopic}
                topics={topics}
                updatedUser={updatedUser}
                setSelected={setSelectedTopic}
              />
            </Flex>
            <PermissionCheckbox
              selected={selectedTopic}
              user={updatedUser}
              topics={topics}
            />
          </Flex>
        </Flex>
      ),
      centered: true,
      labels: { confirm: "Save", cancel: "Cancel" },
      onConfirm: () => onEditSave(),
    });

  const onEditSave = () => {
    console.log(updatedUser);

    userService
      .updateUser(user.id, updatedUser)
      .then(() => {
        reload();
        notifications.show({
          title: "Update Succeed",
          message: "User was successfully updated",
        });
      })
      .catch(() => {
        notifications.show({
          title: "Update Failed",
          message: "User could not be updated",
        });
      });
  };

  return (
    <Table.Tr key={user.id}>
      <Table.Td>
        <Group gap="sm">
          <Text fz="sm" fw={500}>
            {user.username}
          </Text>
        </Group>
      </Table.Td>
      <Table.Td>
        <Text fz="sm">{user.role}</Text>
      </Table.Td>
      <Table.Td>
        <Badge color={statusColors[user.status]} variant="light">
          {user.status}
        </Badge>
      </Table.Td>
      <Table.Td>
        <Text fz="sm">{user.email}</Text>
      </Table.Td>
      <Table.Td>
        <Group gap={0} justify="flex-end">
          <ActionIcon variant="subtle" color="gray" onClick={openModal}>
            <IconPencil
              style={{ width: rem(24), height: rem(24) }}
              stroke={1.5}
            />
          </ActionIcon>
        </Group>
      </Table.Td>
    </Table.Tr>
  );
}
