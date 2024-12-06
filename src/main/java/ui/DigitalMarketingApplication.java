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
import model.InputValidation;

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
       ArrayList<Market> markets = new ArrayList<>();
       for (int i = 0; i < 3; i++) {
           markets.add(new Market(faker.company().industry()));
       }
   
       // Create channels
       ArrayList<Channel> channels = new ArrayList<>();
       for (int i = 0; i < 4; i++) {
           channels.add(new Channel());
       }
   
       // Create products and solution offers
       ArrayList<Product> products = new ArrayList<>();
       ArrayList<SolutionOffer> solutionOffers = new ArrayList<>();
       for (int i = 0; i < 30; i++) {
           Product product = new Product(faker.commerce().productName());
           products.add(product);
   
           // Assign products to a solution offer randomly
           MarketChannelAssignment assignment = new MarketChannelAssignment(
               markets.get(random.nextInt(markets.size())),
               channels.get(random.nextInt(channels.size()))
           );
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
       for (SolutionOffer offer : solutionOffers) {
           ArrayList<String> market = offer.getMarketChannelComb().getMarket().getName();
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
   
   public static void generateAdvertisingEfficiencyReport(ArrayList<SolutionOffer> solutionOffers) {
        System.out.println("Advertising Efficiency Report:");
        for (SolutionOffer offer : solutionOffers) {
            ArrayList<String> market = offer.getMarketChannelComb().getMarket().getName();
            String channel = offer.getMarketChannelComb().getChannel().getName();
            int cost = calculateAdvertisingCost(offer);
      
            System.out.printf("Market: %s, Channel: %s, Advertising Cost: %d\n",
                market, channel, cost);
        }
      }
      
      public static void adminReportsMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Reports Menu:");
        System.out.println("1. Market Profitability");
        System.out.println("2. Channel Profitability");
        System.out.println("3. Advertising Efficiency");
        System.out.print("Enter your choice: ");
        int choice = InputValidation.getValidIntInput("Enter your choice: ", 1, 3);
      
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
      default:
          System.out.println("Invalid choice.");
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
