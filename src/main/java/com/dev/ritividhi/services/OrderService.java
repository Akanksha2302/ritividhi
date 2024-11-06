package com.dev.ritividhi.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.ritividhi.models.Addon;
import com.dev.ritividhi.models.Address;
import com.dev.ritividhi.models.Order;
import com.dev.ritividhi.models.PujaDetails;
import com.dev.ritividhi.models.Temple;
import com.dev.ritividhi.models.Package;
import com.dev.ritividhi.models.User;
import com.dev.ritividhi.repository.AddonRepository;
import com.dev.ritividhi.repository.AddressRepository;
import com.dev.ritividhi.repository.OrderRepository;
import com.dev.ritividhi.repository.PackageRepository;
import com.dev.ritividhi.repository.TempleRepository;

import java.util.Optional;

@Service
public class OrderService {

    private final UserService userService;
    private OrderRepository orderRepository;
    @Autowired
    private PackageRepository packageRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private TempleRepository templeRepository;
    @Autowired
    private AddonRepository addonRepository;
    
    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(String orderId) {
    	
        return orderRepository.findById(orderId);
    }
    public Order createOrder(Order order, String token) {
        System.out.println("token" + token);
        User user = userService.getCurrentUser(token).get();
        String userId = user.getUserId();
        order.setUserId(userId);
        order.setOrderDate(new Date());

        // Fetch the selected package from the database
        if (order.getSelectedPackage() != null && order.getSelectedPackage().getPackageId() != null) {
            String packageId = order.getSelectedPackage().getPackageId();
            System.out.println("Fetching package with ID: " + packageId); // Debug log
            Package selectedPackage = packageRepository.findById(packageId)
                    .orElseThrow(() -> new RuntimeException("Package not found with ID: " + packageId));
            
            // Add order add-ons to the selected package's add-ons if they are not already included
            if (order.getAddOns() != null && !order.getAddOns().isEmpty()) {
                List<Addon> currentAddOns = selectedPackage.getAddOns();
                if (currentAddOns == null) {
                    currentAddOns = new ArrayList<>();
                }
                currentAddOns.addAll(order.getAddOns());
                selectedPackage.setAddOns(currentAddOns);
            }

            order.setSelectedPackage(selectedPackage);
        } else {
            throw new RuntimeException("No package ID provided");
        }

        // Fetch and save the selected address
        if (order.getSelectedAddress() != null && order.getSelectedAddress().getId() != null) {
            String addressId = order.getSelectedAddress().getId();
            System.out.println("Fetching address with ID: " + addressId); // Debug log
            Address selectedAddress = addressRepository.findById(addressId)
                    .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));
            
            order.setSelectedAddress(selectedAddress);
        } else {
            throw new RuntimeException("No address ID provided");
        }

        // Handle add-ons for the order
        List<Addon> orderAddOns = order.getAddOns();
        if (orderAddOns != null) {
            for (Addon addon : orderAddOns) {
                addonRepository.save(addon); // Save each Addon
            }
        }

        // Save PujaDetails and associated Temple details
        List<PujaDetails> pujaDetailsList = order.getPujaDetails();
        if (pujaDetailsList != null) {
            for (PujaDetails pujaDetails : pujaDetailsList) {
                List<Temple> templeDetails = pujaDetails.getTempleDetails();
                if (templeDetails != null) {
                    for (Temple temple : templeDetails) {
                        templeRepository.save(temple); // Save each Temple
                    }
                }
            }
        }

        return orderRepository.save(order);
    }

    
    public void updateOrder(Optional<Order> order) {
        orderRepository.save(order); // Persist the updated order
    }

    public void deleteOrder(String orderId,String token) {
        orderRepository.deleteById(orderId);
    }
}