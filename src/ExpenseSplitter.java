package src;
import java.util.*;

class User {
    String name;
    double balance;

    User(String name) {
        this.name = name;
        this.balance = 0;
    }
}

class ExpenseSplitter {
    static Map<String, User> users = new HashMap<>();

    public static void addUser(String name) {
        users.put(name, new User(name));
    }

    public static void addExpense(String paidBy, double amount, List<String> participants) {
        double splitAmount = amount / participants.size();

        for (String person : participants) {
            users.get(person).balance -= splitAmount;
        }

        users.get(paidBy).balance += amount;
    }

    public static void showBalances() {
        for (User user : users.values()) {
            System.out.println(user.name + " balance: " + user.balance);
        }
    }

    public static void simplifyDebts() {
        List<User> creditors = new ArrayList<>();
        List<User> debtors = new ArrayList<>();

        for (User user : users.values()) {
            if (user.balance > 0) creditors.add(user);
            else if (user.balance < 0) debtors.add(user);
        }

        for (User debtor : debtors) {
            for (User creditor : creditors) {
                if (debtor.balance == 0) break;

                double amount = Math.min(-debtor.balance, creditor.balance);

                if (amount > 0) {
                    System.out.println(debtor.name + " pays " + amount + " to " + creditor.name);
                    debtor.balance += amount;
                    creditor.balance -= amount;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add User");
            System.out.println("2. Add Expense");
            System.out.println("3. Show Balances");
            System.out.println("4. Simplify Debts");
            System.out.println("5. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    addUser(name);
                    break;

                case 2:
                    System.out.print("Paid by: ");
                    String paidBy = sc.nextLine();

                    System.out.print("Amount: ");
                    double amount = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Number of participants: ");
                    int n = sc.nextInt();
                    sc.nextLine();

                    List<String> participants = new ArrayList<>();
                    for (int i = 0; i < n; i++) {
                        System.out.print("Participant " + (i + 1) + ": ");
                        participants.add(sc.nextLine());
                    }

                    addExpense(paidBy, amount, participants);
                    break;

                case 3:
                    showBalances();
                    break;

                case 4:
                    simplifyDebts();
                    break;

                case 5:
                    System.exit(0);
            }
        }
    }
}