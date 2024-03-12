import { useEffect, useState } from "react";
import userService from "../../services/user.service";
import { Flex, Table } from "@mantine/core";
import { User } from "./User";
import topicService from "../../services/topic.service";

export function ManageUsers() {
  const [users, setUsers] = useState<any>();
  const [topics, setTopics] = useState<any>();

  useEffect(() => {
    reloadUsers();

    topicService
      .getAllWithoutImage()
      .then((res: any) => {
        setTopics(res);
      })
      .catch((err: any) => {
        console.log(err);
      });
  }, []);

  const reloadUsers = () => {
    userService
      .getAll()
      .then((res: any) => {
        setUsers(res);
      })
      .catch((err: any) => {
        console.log(err);
      });
  };

  return (
    <Flex direction="column" justify="center" align="center" mt="lg">
      <Table.ScrollContainer minWidth={800}>
        <Table verticalSpacing="md">
          <Table.Thead>
            <Table.Tr>
              <Table.Th>Username</Table.Th>
              <Table.Th>Role</Table.Th>
              <Table.Th>Status</Table.Th>
              <Table.Th>Email</Table.Th>
              <Table.Th />
            </Table.Tr>
          </Table.Thead>
          <Table.Tbody>
            {users &&
              topics &&
              users.map((u: any) => (
                <User
                  key={u.id}
                  user={u}
                  topics={topics}
                  reload={reloadUsers}
                />
              ))}
          </Table.Tbody>
        </Table>
      </Table.ScrollContainer>
    </Flex>
  );
}
