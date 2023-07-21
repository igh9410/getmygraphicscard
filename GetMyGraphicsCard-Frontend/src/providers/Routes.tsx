import { Landing, LogIn, Root, Search, SignUp, User } from '@pages';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';

export function Routes() {
  const router = createBrowserRouter([
    {
      path: '/',
      element: <Root />,
      children: [
        {
          path: '/',
          element: <Landing />,
        },
        {
          path: '/search',
          element: <Search />,
        },
        {
          path: '/user',
          element: <User />,
        },
      ],
    },
    {
      path: '/login',
      element: <LogIn />,
    },
    {
      path: '/signup',
      element: <SignUp />,
    },
  ]);

  return <RouterProvider router={router} />;
}
