package restful;

import entities.main.Product;
import model.AdminMarketModel;
import model.database.ProductAndCategoryManager;
import servlets.DAOException;


import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
public class ProductService {

    @EJB
    private ProductAndCategoryManager model;

    @GET
    @Produces("application/json")
    public List<Product> getProducts() throws DAOException {
        return model.getProducts();
    }

    @Path("{id}")
    @GET
    @Produces("application/json")
    public Product getProduct(@PathParam("id") int id) throws DAOException {
        return model.getProduct(id);
    }

    @Path("create")
    @POST
    @Produces("application/json")
    public Response createProduct(Product product) throws DAOException {
        try {
            model.validateProduct(product);
        }
        catch (DAOException e){
            return Response.status(400).entity(product).build();
        }
        model.createProduct(product);
        return Response.status(201).entity(product).build();
    }

    @Path("update")
    @PUT
    @Produces("application/json")
    public Response updateProduct(Product product) throws DAOException {
        try {
            model.validateProduct(product);
        }
        catch (DAOException e){
            return Response.status(400).entity(product).build();
        }
        model.editProduct(product);
        return Response.status(200).entity(product).build();
    }

    @Path("delete/{id}")
    @DELETE
    @Produces("application/json")
    public Response deleteProduct(@PathParam("id") int id) throws DAOException {
        Product product = model.getProduct(id);
        if (product == null) return Response.status(404).entity(product).build();
        model.deleteProduct(id);
        return Response.status(200).entity(product).build();
    }
}
