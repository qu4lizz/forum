import { Outlet, Navigate } from "react-router-dom";
import { RootState, useAppSelector } from "../../redux";
import { AuthUser } from "../../types/types";

interface Roles {
  roles: string[];
}

export function PrivateRoutes({ roles }: Roles) {
  const user: AuthUser = useAppSelector((state: RootState) => state.user);
  let permitted = false;

  if (user.role) permitted = roles.includes(user.role);

  return permitted ? <Outlet /> : <Navigate to="/" />;
}
