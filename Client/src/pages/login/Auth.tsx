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
  PinInput,
  Center,
} from "@mantine/core";
import classes from "./Auth.module.css";
import { GoogleButton } from "./GoogleButton";
import { useToggle } from "@mantine/hooks";
import { useEffect, useState } from "react";
import { useForm } from "@mantine/form";
import authService from "../../services/auth.service";
import { notifications } from "@mantine/notifications";
import { login } from "../../redux/slices/userSlice";
import { useAppDispatch } from "../../redux";
import oauthService from "../../services/oauth.service";

interface Props {
  req: "Login" | "Register";
  close: any;
}

export function Auth({ req, close }: Props) {
  const [type, toggle] = useToggle(["Login", "Register"]);
  const [isStepOne, setStepOne] = useState<boolean>(true);
  const [verificationCode, setVerificationCode] = useState<string>("");
  const dispatch = useAppDispatch();
  const [oauthUrl, setOauthUrl] = useState<string>();

  useEffect(() => {
    toggle(req);

    oauthService
      .getOauthUrl()
      .then((res: any) => {
        console.log(res);
        setOauthUrl(res.authURL);
      })
      .catch((err: any) => console.log(err));
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

  const submit = async () => {
    if (type === "Register") {
      authService
        .register(form.values)
        .then(() => {
          notifications.show({
            title: "Successful Registration",
            message:
              "You have registered. You will get a email when admin approves your account.",
          });
          close();
        })
        .catch((res: any) => {
          if (res.response.status === 400) {
            notifications.show({
              title: "Registration Failed",
              message: res.response.data,
            });
          } else {
            notifications.show({
              title: "Registration Failed",
              message: "Server Error",
            });
          }
        });
    } else {
      authService
        .login({
          username: form.values.username,
          password: form.values.password,
        })
        .then((res: any) => {
          notifications.show({
            title: "Email Code",
            message: "You received login code on email.",
          });
          setStepOne(false);
          sessionStorage.setItem("id", res.tempId);
        })
        .catch((res: any) => {
          console.log(res);
          if (res.response.status === 400 || res.response.status == 401) {
            notifications.show({
              title: "Login Failed",
              message: res.response.data,
            });
          } else {
            notifications.show({
              title: "Login Failed",
              message: "Server Error",
            });
          }
        });
    }
  };

  const submitVerification = async () => {
    if (verificationCode.length !== 6) {
      notifications.show({
        title: "Error",
        message: "Verification code needs to have 6 characters",
      });
      return;
    }

    await dispatch(
      login({
        code: verificationCode,
        id: sessionStorage.getItem("id"),
      })
    )
      .then(() => {
        close();
      })
      .catch((res: any) => {
        notifications.show({
          title: "Login Failed",
          message: res.response.data,
        });
      });
  };

  return (
    <Paper className={classes.form} radius={15} p={30}>
      <Title order={2} className={classes.title} ta="center" mb={50}>
        Welcome to Forum!
      </Title>

      {isStepOne && (
        <>
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
            <GoogleButton
              radius="xl"
              onClick={() => {
                if (oauthUrl) window.location.href = oauthUrl;
                console.log(oauthUrl);
              }}
            >
              Google
            </GoogleButton>
          </Group>
        </>
      )}

      {!isStepOne && (
        <>
          <Text size="lg" ta="center">
            You have received 6 character code on email, enter it
          </Text>
          <Center>
            <PinInput
              mt="lg"
              length={6}
              onChange={(target) => setVerificationCode(target)}
            />
          </Center>
          <Button fullWidth mt="xl" size="md" onClick={submitVerification}>
            {type}
          </Button>
        </>
      )}
    </Paper>
  );
}
