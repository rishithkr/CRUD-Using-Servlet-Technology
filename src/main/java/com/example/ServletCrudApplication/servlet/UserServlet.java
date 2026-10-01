package com.example.ServletCrudApplication.servlet;

import com.example.ServletCrudApplication.model.User;
import com.example.ServletCrudApplication.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService(); // TIGHT COUPLING

    @Override
    public void doGet(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws IOException {
        String idInString = httpServletRequest.getParameter("id");
        if(idInString == null){
            List<User> userList = userService.getAllUsers();
            if (userList == null) {
                httpServletResponse.setStatus(404);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().write(
                        "{\n" +
                                "   \"message\" :\"Error! No users were found\"\n" +
                                "}"
                );
            }
            else{
                httpServletResponse.setStatus(200);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().write(usersToJson(userList));
            }
        }

        else {
            Integer id = Integer.parseInt(idInString);
            User userResp = userService.getUserById(id);
            if (userResp == null){
                httpServletResponse.setStatus(404);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().write(
                        "{\n" +
                                "   \"message\" :\"Error! User was not found\"\n" +
                                "}"
                );

            }
            else{
                httpServletResponse.setStatus(200);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().write(userToJson(userResp));
            }
        }

    }

    @Override
    public void doPost(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws IOException {

        String idInString = httpServletRequest.getParameter("id") ;
        String name = httpServletRequest.getParameter("name");
        String mobile = httpServletRequest.getParameter("mobile");
        String email = httpServletRequest.getParameter("email");
        if(idInString == null || name == null || mobile == null || email == null)
        {
            httpServletResponse.setStatus(400);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(
                    "{\n" +
                            "   \"message\" :\"Error! Some fields were missing\"\n" +
                            "}"
            );
            return;
        }
        Integer id = Integer.parseInt(idInString);
        User user = new User(id, name, email, mobile);
        User createdUser = userService.createUser(user);
        httpServletResponse.setStatus(201);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(
                        "   \"message\" :\"User added successfully\"\n" + userToJson(createdUser)

        );
    }

    @Override
    public void doDelete(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws IOException {
        String idInString = httpServletRequest.getParameter("id");
        if(idInString == null)
        {
            httpServletResponse.setStatus(400);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(
                    "{\n" +
                            "   \"message\" :\"Error! ID was missing\"\n" +
                            "}"
            );
            return;
        }
        Integer id = Integer.parseInt(idInString);
        User deletedUser = userService.deleteUser(id);
        httpServletResponse.setStatus(201);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(
                        "   \"message\" :\"User deleted successfully\"\n" + userToJson(deletedUser)
        );
    }

    @Override
    public void doPut(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws IOException {

        String idInString = httpServletRequest.getParameter("id");
        String name = httpServletRequest.getParameter("name");
        String mobile = httpServletRequest.getParameter("mobile");
        String email = httpServletRequest.getParameter("email");
        if(idInString == null)
        {
            httpServletResponse.setStatus(400);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(
                    "{\n" +
                            "   \"message\" :\"Error! ID was missing\"\n" +
                            "}"
            );
            return;
        }
        Integer id = Integer.parseInt(idInString);
        User updatedUser = userService.updateUser(id, name, email, mobile);
        if (updatedUser == null)
        {
            httpServletResponse.setStatus(404);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(
                    "{\n" +
                            "   \"message\" :\"Error! User was not found\"\n" +
                            "}"
            );
        }
        else{
            httpServletResponse.setStatus(201);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(
                            "   \"message\" :\"User updated successfully\"\n" + userToJson(updatedUser)
            );
        }
    }


    private String userToJson(User userResp)
    {
        return "{\n" +
                "   \"id\" :\"" + userResp.getId() + "\",\n" +
                "   \"name\" :\"" + userResp.getName() + "\",\n" +
                "   \"email\" :\"" + userResp.getEmail() + "\",\n" +
                "   \"mobile\" :\"" + userResp.getMobile() + "\"\n" +
                "}\n";
    }

    private String usersToJson(List<User> userList)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[\n");
        for(int i = 0; i < userList.size(); i++) {
            stringBuilder.append(userToJson(userList.get(i)));
            if (i < userList.size() - 1 )
                stringBuilder.append(",");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
