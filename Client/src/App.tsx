import { Container } from "@mantine/core";
import { BrowserRouter } from "react-router-dom";
import { NavBar } from "./components/NavBar";

function App() {
  return (
    <BrowserRouter>
      <Container size="100%" h="100vh" px={0}>
        <NavBar />
        abc
      </Container>
    </BrowserRouter>
  );
}

export default App;
