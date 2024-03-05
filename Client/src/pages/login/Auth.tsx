import {
  Paper,
  TextInput,
  PasswordInput,
  Button,
  Title,
  Text,
  Anchor,
  Divider,
  Group,
} from "@mantine/core";
import classes from "./Auth.module.css";
import { GoogleButton } from "./GoogleButton";
import { useToggle } from "@mantine/hooks";
import { useEffect } from "react";
import { useForm } from "@mantine/form";
import authService from "../../services/auth.service";

interface Props {
  req: "Login" | "Register";
}

export function Auth({ req }: Props) {
  const [type, toggle] = useToggle(["Login", "Register"]);

  useEffect(() => {
    toggle(req);
  }, [req]);

  const form = useForm({
    initialValues: {
      email: "",
      username: "",
      password: "",
    },

    validate: {
      username: (val: string) =>
        val.trim().length == 0 ? "Enter username" : null,
      email: (val: string) =>
        type === "Register"
          ? /^\S+@\S+$/.test(val)
            ? null
            : "Invalid email"
          : null,
      password: (val: string) =>
        val.length <= 6
          ? "Password should include at least 6 characters"
          : null,
    },
  });

  const submit = () => {
    console.log(form.values);
    if (type === "Register") {
      authService.register(form.values);
    } else {
      authService.login({
        username: form.values.username,
        password: form.values.password,
      });
    }
  };

  return (
    <Paper className={classes.form} radius={15} p={30}>
      <Title order={2} className={classes.title} ta="center" mb={50}>
        Welcome to Forum!
      </Title>

      <form onSubmit={form.onSubmit(() => submit())}>
        {type === "Register" && (
          <TextInput
            withAsterisk
            label="Email"
            size="md"
            value={form.values.email}
            onChange={(event) =>
              form.setFieldValue("email", event.currentTarget.value)
            }
            error={form.errors.email}
            radius="md"
          />
        )}

        <TextInput
          withAsterisk
          label="Username"
          size="md"
          mt="md"
          value={form.values.username}
          onChange={(event) =>
            form.setFieldValue("username", event.currentTarget.value)
          }
          error={form.errors.username}
          radius="md"
        />

        <PasswordInput
          withAsterisk
          label="Password"
          mt="md"
          size="md"
          value={form.values.password}
          onChange={(event) =>
            form.setFieldValue("password", event.currentTarget.value)
          }
          error={form.errors.password}
          radius="md"
        />

        <Button fullWidth mt="xl" size="md" type="submit">
          {type}
        </Button>
      </form>

      <Text ta="center" mt="md">
        {type === "Register"
          ? "Already have an account?"
          : "Don't have an account?"}{" "}
        <Anchor<"a">
          fw={700}
          onClick={() => {
            toggle();
            form.reset();
          }}
        >
          {type === "Register" ? "Login" : "Register"}
        </Anchor>
      </Text>
      <Divider label="OR" labelPosition="center" my="lg" />
      <Group grow mb="md" mt="md">
        <GoogleButton radius="xl">Google</GoogleButton>
      </Group>
    </Paper>
  );
}
