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
} from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import classes from "./NavBar.module.css";
import { useNavigate } from "react-router-dom";
import { Auth } from "../pages/login/Auth";
import { useState } from "react";

export function NavBar() {
  const navigate = useNavigate();

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
      >
        <Auth req={type} />
      </Modal>
      <header className={classes.header}>
        <Group justify="space-between" h="100%">
          <Title order={1}>Forum</Title>
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
          </Group>
          <Burger
            opened={drawerOpened}
            onClick={toggleDrawer}
            hiddenFrom="sm"
          />
        </Group>
      </header>

      <Drawer
        opened={drawerOpened}
        onClose={closeDrawer}
        size="100%"
        padding="md"
        title="Navigation"
        hiddenFrom="sm"
        zIndex={1000000}
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
            <Button variant="default">Log in</Button>
            <Button>Sign up</Button>
          </Group>
        </ScrollArea>
      </Drawer>
    </Box>
  );
}
