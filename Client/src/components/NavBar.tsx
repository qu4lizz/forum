import {
  Group,
  Button,
  Divider,
  Box,
  Burger,
  Drawer,
  ScrollArea,
  rem,
  Title,
  Modal,
  Menu,
} from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import classes from "./NavBar.module.css";
import { useNavigate } from "react-router-dom";
import { Auth } from "../pages/login/Auth";
import { useState } from "react";
import { RootState, useAppDispatch, useAppSelector } from "../redux";
import { Logout } from "tabler-icons-react";
import { logout } from "../redux/slices/userSlice";

export function NavBar() {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();

  const username: any = useAppSelector(
    (state: RootState) => state.user.username
  );

  const [drawerOpened, { toggle: toggleDrawer, close: closeDrawer }] =
    useDisclosure(false);

  const [opened, { open, close }] = useDisclosure(false);
  const [type, setType] = useState<"Login" | "Register">("Login");

  return (
    <Box>
      <Modal
        opened={opened}
        onClose={close}
        centered
        withCloseButton={false}
        overlayProps={{
          backgroundOpacity: 0.55,
          blur: 3,
        }}
        padding={0}
        radius={15}
        zIndex={2000}
      >
        <Auth req={type} close={close} />
      </Modal>
      <header className={classes.header}>
        <Group justify="space-between" h="100%">
          <Title
            order={1}
            onClick={() => navigate("/")}
            className={classes.title}
          >
            Forum
          </Title>
          <Group h="100%" gap={0} visibleFrom="sm">
            <a href="#" className={classes.link}>
              Home
            </a>
            <a href="#" className={classes.link}>
              Learn
            </a>
            <a href="#" className={classes.link}>
              Academy
            </a>
          </Group>

          <Group visibleFrom="sm">
            {!username ? (
              <>
                <Button
                  variant="default"
                  onClick={() => {
                    setType("Login");
                    open();
                  }}
                >
                  Log in
                </Button>
                <Button
                  onClick={() => {
                    setType("Register");
                    open();
                  }}
                >
                  Sign up
                </Button>
              </>
            ) : (
              <Menu shadow="md" width={200}>
                <Menu.Target>
                  <Button>{username}</Button>
                </Menu.Target>

                <Menu.Dropdown>
                  <Menu.Item
                    leftSection={
                      <Logout style={{ width: rem(14), height: rem(14) }} />
                    }
                    onClick={() => dispatch(logout())}
                  >
                    Logout
                  </Menu.Item>
                </Menu.Dropdown>
              </Menu>
            )}
          </Group>

          <Burger
            opened={drawerOpened}
            onClick={toggleDrawer}
            hiddenFrom="sm"
          />

          {}
        </Group>
      </header>

      <Drawer
        opened={drawerOpened}
        onClose={closeDrawer}
        size="100%"
        padding="md"
        title="Navigation"
        hiddenFrom="sm"
        zIndex={1500}
      >
        <ScrollArea h={`calc(100vh - ${rem(80)})`} mx="-md">
          <Divider my="sm" />

          <a href="#" className={classes.link}>
            Home
          </a>
          <a href="#" className={classes.link}>
            Learn
          </a>
          <a href="#" className={classes.link}>
            Academy
          </a>

          <Divider my="sm" />

          <Group justify="center" grow pb="xl" px="md">
            {!username ? (
              <>
                <Button
                  variant="default"
                  onClick={() => {
                    setType("Login");
                    open();
                    closeDrawer();
                  }}
                >
                  Log in
                </Button>
                <Button
                  onClick={() => {
                    setType("Register");
                    open();
                    closeDrawer();
                  }}
                >
                  Sign up
                </Button>
              </>
            ) : (
              <Menu shadow="md" width={200} zIndex={2400}>
                <Menu.Target>
                  <Button>{username}</Button>
                </Menu.Target>

                <Menu.Dropdown>
                  <Menu.Item
                    leftSection={
                      <Logout style={{ width: rem(14), height: rem(14) }} />
                    }
                    onClick={() => dispatch(logout())}
                  >
                    Logout
                  </Menu.Item>
                </Menu.Dropdown>
              </Menu>
            )}
          </Group>
        </ScrollArea>
      </Drawer>
    </Box>
  );
}
