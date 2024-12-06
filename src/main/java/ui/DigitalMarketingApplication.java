/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.*;

import com.github.javafaker.Faker;

import model.Business.Business;
import model.Business.ConfigureABusiness;
import model.MarketModel.Channel;
import model.MarketModel.Market;
import model.MarketModel.MarketChannelAssignment;
import model.ProductManagement.Product;
import model.ProductManagement.SolutionOffer;
import model.Validation.InputValidation;
import model.Adscost.AdvertisingCosts;

/**
 *
 * @author kal bugrara
 */
public class DigitalMarketingApplication {

  /**
   * @param args the command line arguments
   */

   static ArrayList<SolutionOffer> solutionOffers = new ArrayList<>();
     public static void main(String[] args) {
   
       Business business = ConfigureABusiness.createABusinessAndLoadALotOfData("Amazon", 50, 10, 30, 100, 10);
       Faker faker = new Faker();
       Random random = new Random();
   
       // Create markets
       Market market1 = new Market ("Students");
       Market market2 = new Market ("Professionals");
       Market market3 = new Market ("Seniors");

       ArrayList<Market> markets = new ArrayList<>();
       for (int i = 0; i < 3; i++) {
           markets.add(new Market(faker.company().industry()));
       }
   
       // Create channels
       Channel channel1 = new Channel ("Television");
       Channel channel2 = new Channel ("Radio");
       Channel channel3 = new Channel ("Newspaper");
       Channel channel4 = new Channel ("Internet");

       ArrayList<Channel> channels = new ArrayList<>();
       for (int i = 0; i < 4; i++) {
                channels.add(new Channel(faker.company().name()));
                
       }
   
       // Create products and solution offers
       ArrayList<Product> products = new ArrayList<>();
       for (int i = 0; i < 30; i++) {
           Product product = new Product(faker.commerce().productName());
           products.add(product);
   
           // Assign products to a solution offer randomly
           MarketChannelAssignment assignment = new MarketChannelAssignment(
               markets.get(random.nextInt(markets.size())),
               channels.get(random.nextInt(channels.size()))              
           );
           System.out.println("Assigned Channel: " + assignment.getChannel().getName());
           SolutionOffer offer = new SolutionOffer(assignment);
           offer.addProduct(product);
           offer.setPrice(faker.number().numberBetween(10, 500));
           solutionOffers.add(offer);
           System.out.println("Data model populated with markets, channels, and products."); 
         }

         Scanner scanner = new Scanner(System.in);
    while (true) {
        System.out.println("\nMain Menu:");
        System.out.println("1. Admin Reports");
        System.out.println("2. Customer Shopping");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                adminReportsMenu();
                break;
            case 2:
                customerShoppingMenu(solutionOffers);
                break;
            case 0:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
  }
   
       private static int calculateAdvertisingCost(SolutionOffer offer) {
         return new Random().nextInt(1000); // Simulated cost for now
   }
   
     public static void generateMarketProfitabilityReport(ArrayList<SolutionOffer> solutionOffers) {
       System.out.println("Market Profitability Report:");
       System.out.println("Solution offers size: " + solutionOffers.size());
       for (SolutionOffer offer : solutionOffers) {
           ArrayList<String> market = offer.getMarketChannelComb().getMarket().getName();
           System.out.println("Market: " + offer.getMarketChannelComb().getMarket().getName());
           int revenue = offer.getPrice();
           int advertisingCost = calculateAdvertisingCost(offer);
           int profit = revenue - advertisingCost;
   
           System.out.printf("Market: %s, Revenue: %d, Advertising Cost: %d, Profit: %d\n",
               market, revenue, advertisingCost, profit);
       }
   }
   
   public static void generateChannelProfitabilityReport(ArrayList<SolutionOffer> solutionOffers) {
     System.out.println("Channel Profitability Report:");
     for (SolutionOffer offer : solutionOffers) {
         String channel = offer.getMarketChannelComb().getChannel().getName();
         int revenue = offer.getPrice();
         int advertisingCost = calculateAdvertisingCost(offer);
   
         System.out.printf("Channel: %s, Revenue: %d, Advertising Cost: %d\n",
             channel, revenue, advertisingCost);
     }
   }
   
   public static void generateAdvertisingEfficiencyReport() {
    System.out.println("Advertising Efficiency Report:");

    Map<String, Map<String, Integer>> costs = AdvertisingCosts.getAllCosts();
    int grandTotal = 0;

    // Print table header
    System.out.printf("%-15s %-15s %-15s %-15s %-15s\n", "Channel", "Market 1", "Market 2", "Market 3", "Total");

    for (String channel : costs.keySet()) {
        int channelTotal = 0;

        // Start row for each channel
        System.out.printf("%-15s", channel);

        for (Map.Entry<String, Integer> entry : costs.get(channel).entrySet()) {
            int cost = entry.getValue();
            channelTotal += cost;
            grandTotal += cost;

            // Print each market's cost
            System.out.printf("%-15d", cost);
        }

        // Print total for the channel
        System.out.printf("%-15d\n", channelTotal);
    }

    // Print totals row
    System.out.print("Total          ");
    int market1Total = costs.values().stream().mapToInt(m -> m.getOrDefault("Market 1", 0)).sum();
    int market2Total = costs.values().stream().mapToInt(m -> m.getOrDefault("Market 2", 0)).sum();
    int market3Total = costs.values().stream().mapToInt(m -> m.getOrDefault("Market 3", 0)).sum();

    System.out.printf("%-15d %-15d %-15d %-15d\n", market1Total, market2Total, market3Total, grandTotal);
}

      
      public static void adminReportsMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Reports Menu:");
            System.out.println("1. Market Profitability");
            System.out.println("2. Channel Profitability");
            System.out.println("3. Advertising Efficiency");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
    
            int choice = InputValidation.getValidIntInput("Enter your choice: ", 1, 4);
    
            switch (choice) {
                case 1:
                    generateMarketProfitabilityReport(solutionOffers);
                    break;
                case 2:
                    generateChannelProfitabilityReport(solutionOffers);
                    break;
                case 3:
                    generateAdvertisingEfficiencyReport(solutionOffers);
                    break;
                case 4:
                    System.out.println("Exiting Reports Menu...");
                    exit = true; // Exit the loop
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println(); // Add spacing for better readability
        }
        
}

public static void customerShoppingMenu(ArrayList<SolutionOffer> solutionOffers) {
  Scanner scanner = new Scanner(System.in);
  ArrayList<SolutionOffer> cart = new ArrayList<>();

  System.out.println("Available Products:");
  for (int i = 0; i < solutionOffers.size(); i++) {
      SolutionOffer offer = solutionOffers.get(i);
      System.out.printf("%d. Product: %s, Price: %d\n", i + 1, 
          offer.getProducts().get(0).getName(), offer.getPrice());
  }

  System.out.print("Enter product number to add to cart (0 to checkout): ");
  int choice;
  while ((choice = scanner.nextInt()) != 0) {
      cart.add(solutionOffers.get(choice - 1));
      System.out.print("Enter next product number (0 to checkout): ");
  }

  System.out.println("Order Confirmation:");
  for (SolutionOffer offer : cart) {
      System.out.printf("Product: %s, Price: %d\n",
          offer.getProducts().get(0).getName(), offer.getPrice());
  }
}

}
