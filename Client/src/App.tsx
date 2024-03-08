import { Container } from "@mantine/core";
import { Route, Routes } from "react-router-dom";
import { NavBar } from "./components/NavBar";
import { Notifications } from "@mantine/notifications";
import { Home } from "./pages/home/Home";
import { Topic } from "./pages/topic/Topic";

function App() {
  return (
    <>
      <Notifications position="top-right" zIndex={2500} />
      <NavBar />
      <Container size="100%" h="100vh" px={0}>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/topics/:id" element={<Topic />} />
        </Routes>
      </Container>
    </>
  );
}

export default App;
