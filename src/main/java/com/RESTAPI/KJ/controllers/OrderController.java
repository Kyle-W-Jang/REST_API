package com.RESTAPI.KJ.controllers;

import com.RESTAPI.KJ.models.ApiError;
import com.RESTAPI.KJ.models.Order;
import com.RESTAPI.KJ.services.OrderServiceImpl;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;



import java.util.List;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Operation(summary = "POST Operations on Order", description = "Create Order API pertaining to an Order object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of an Order"),
            @ApiResponse(responseCode = "400", description = "Bad Request Parameter",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@Parameter(description = "Order object to be created: Please look at the /submission/README.MD for details", required = true) @Validated @RequestBody Order order) {
        logger.info("CREATE API was triggered");
        Order savedOrder = orderServiceImpl.createOrder(order);
        logger.info("CREATE an Order: "  + savedOrder.toString());
        return ResponseEntity.ok(savedOrder);
    }

    @Operation(summary = "GET Operations on Order", description = "Get Order API pertaining to an Order object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of order"),
            @ApiResponse(responseCode = "400", description = "Bad Request Parameter",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}/get")
    public ResponseEntity<Order> getOrderById(@Parameter(description = "ID for an object to be retrieved: Has to be a positive number", required = true)@PathVariable Long id) {
        logger.info("GET API was triggered");
        Order order = orderServiceImpl.getOrderById(id);
        logger.info("Retrieved an Order: "  + order.toString());
        return ResponseEntity.ok(order);
    }


    @Operation(summary = "PUT Operations on Order", description = "Update Order API pertaining to an Order object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful update of an order"),
            @ApiResponse(responseCode = "400", description = "Bad Request Parameter",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/{id}/update")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Parameter(description = "Order object to be updated: Please look at the /submission/README.MD for details", required = true) @Validated @RequestBody Order orderDetails) {
        logger.info("UPDATE API was triggered");
        Order updatedOrder = orderServiceImpl.updateOrder(id, orderDetails);
        logger.info("Order after an Update: "  + updatedOrder.toString());
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "DELETE Operations on Order", description = "Remove Order API pertaining to Order object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful removal of an order"),
            @ApiResponse(responseCode = "400", description = "Bad Request Parameter",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteOrder(@Parameter(description = "ID for an object to be removed: Has to be a positive number", required = true)@PathVariable Long id) {
        logger.info("DELETE API was triggered");
        orderServiceImpl.deleteOrder(id);
        logger.info("Order Id: " + id + " " + "successfully removed.");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "GET Operations on ALL Orders", description = "GET Order API pertaining to all Order objects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of all existing orders"),
            @ApiResponse(responseCode = "400", description = "Bad Request Parameter",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/listAll")
    public ResponseEntity<List<Order>> getAllOrders() {
        logger.info("LIST_ALL API was triggered");
        List<Order> orders = orderServiceImpl.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
