import React from "react";
import "./Container.css";

type ContainerProps = {
  children: React.ReactNode;
};
function Container({ children }: ContainerProps) {
  return <div className="container">{children}</div>;
}

export default Container;
