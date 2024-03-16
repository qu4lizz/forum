import { Container } from "@mantine/core";
import { Route, Routes, useLocation, useNavigate } from "react-router-dom";
import { NavBar } from "./components/NavBar";
import { Notifications, notifications } from "@mantine/notifications";
import { Home } from "./pages/home/Home";
import { Topic } from "./pages/topic/Topic";
import { ManageComments } from "./pages/manage-comments/ManageComments";
import { PrivateRoutes } from "./pages/guard/PrivateRoutes";
import { ManageUsers } from "./pages/manage-users/ManageUsers";
import { useEffect, useRef } from "react";
import { useAppDispatch } from "./redux";
import { oAuth2Login } from "./redux/slices/userSlice";

function App() {
  const location = useLocation();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const hasRequested = useRef(false); // Use a ref instead of state

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const code = searchParams.get("code");

    if (code && !hasRequested.current) {
      hasRequested.current = true; // Mark the request as made
      dispatch(oAuth2Login(code))
        .then(() => {
          notifications.show({
            title: "Sign Up with Google Successful",
            message: "You have signed up with Google",
          });
        })
        .catch((err: any) => {
          notifications.show({
            title: "Sign Up with Google Failed",
            message: err.response.data,
          });
        })
        .finally(() => navigate("/"));
    }
  }, [location.search, dispatch]);

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
