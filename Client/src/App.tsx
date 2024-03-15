import { Container } from "@mantine/core";
import { Route, Routes, useParams } from "react-router-dom";
import { NavBar } from "./components/NavBar";
import { Notifications } from "@mantine/notifications";
import { Home } from "./pages/home/Home";
import { Topic } from "./pages/topic/Topic";
import { ManageComments } from "./pages/manage-comments/ManageComments";
import { PrivateRoutes } from "./pages/guard/PrivateRoutes";
import { ManageUsers } from "./pages/manage-users/ManageUsers";
import { useEffect } from "react";
import oauthService from "./services/oauth.service";

function App() {
  const { code } = useParams();

  useEffect(() => {
    if (code) {
      console.log("doingcode:", code);
      oauthService
        .callbackToken(code)
        .then((res: any) => {
          console.log(res);
        })
        .catch((err: any) => {
          console.log(err);
        });
    }
  }, [code]);

  return (
    <>
      <Notifications position="top-right" zIndex={2500} />
      <NavBar />
      <Container size="100%" h="100vh" px={0}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/topics/:id" element={<Topic />} />

          <Route element={<PrivateRoutes roles={["ADMIN", "MODERATOR"]} />}>
            <Route path="/manage-comments" element={<ManageComments />} />
            <Route path="/users" element={<ManageUsers />} />
          </Route>
        </Routes>
      </Container>
    </>
  );
}

export default App;
