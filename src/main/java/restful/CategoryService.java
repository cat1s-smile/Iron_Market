package restful;

import entities.main.Category;
import model.AdminMarketModel;
import servlets.DAOException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
public class CategoryService {
    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    @GET
    @Produces("application/json")
    public List<Category> getCategories() throws DAOException {
        return model.getCategories();
    }

    @Path("{id}")
    @GET
    @Produces("application/json")
    public Category getCategory(@PathParam("id") int id) throws DAOException {
        return model.getCategory(id);
    }

    @Path("create")
    @POST
    @Produces("application/json")
    public Response createCategory(Category category) throws DAOException {
        model.createCategory(category);
        return Response.status(200).entity(category).build();
    }

    @Path("update")
    @PUT
    @Produces("application/json")
    public Response updateCategory(Category category) throws DAOException {
        model.editCategory(category);
        return Response.status(200).entity(category).build();
    }

    @Path("delete/{id}")
    @DELETE
    @Produces("application/json")
    public Response deleteCategory(@PathParam("id") int id) throws DAOException {
        Category category = model.getCategory(id);
        model.deleteCategory(id);
        return Response.status(200).entity(category).build();
    }
}

