package restful;

import entities.main.Product;
import model.AdminMarketModel;
import servlets.DAOException;


import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
public class ProductService {

    @EJB(beanName = "DBAdminMarketModel")
    private AdminMarketModel model;

    @GET
    @Produces("application/json")
    public List<Product> getProducts(){
        return model.getProducts();
    }

    @Path("{id}")
    @GET
    @Produces("application/json")
    public Product getProduct(@PathParam("id") int id){
        return model.getProduct(id);
    }

    @Path("create")
    @POST
    @Produces("application/json")
    public Response createProduct(Product product){
        model.createProduct(product);
        return Response.status(200).entity(product).build();
    }

    @Path("update")
    @PUT
    @Produces("application/json")
    public Response updateProduct(Product product){
        model.editProduct(product);
        return Response.status(200).entity(product).build();
    }

    @Path("delete/{id}")
    @DELETE
    @Produces("application/json")
    public Response deleteProduct(@PathParam("id") int id) throws DAOException {
        Product product = model.getProduct(id);
        model.deleteProduct(id);
        return Response.status(200).entity(product).build();
    }
}
