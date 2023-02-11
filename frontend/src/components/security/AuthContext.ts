import { createContext, useContext, useState } from "react";

type AuthContextType = "false" | "true";

type AuthProps = {
  children: React.ReactNode;
};

//1: Create a Context
export const AuthContext = createContext<AuthContextType>("false");

export const useAuth = () => useContext(AuthContext);

//2: Share the created context with other components
export default function AuthProvider({ children: AuthProps }) {
  //3: Put some state in the context

  const [isAuthenticated, setAuthenticated] = useState(false);

  const [username, setUsername] = useState(null);

  const [token, setToken] = useState(null);

  async function login(email: string, password: string) {
    const baToken = "Basic " + window.btoa(username + ":" + password);

    try {
      const response = await baToken;

      if (response.status == 200) {
        setAuthenticated(true);
        setUsername(username);
        setToken(baToken);

        apiClient.interceptors.request.use((config) => {
          console.log("intercepting and adding a token");
          config.headers.Authorization = baToken;
          return config;
        });

        return true;
      } else {
        logout();
        return false;
      }
    } catch (error) {
      logout();
      return false;
    }
  }

  function logout() {
    setAuthenticated(false);
    setToken(null);
    setUsername(null);
  }

  return (
   
  );
}
