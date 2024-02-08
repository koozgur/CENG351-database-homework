package ceng.ceng351.cengfactorydb;

import javax.sound.midi.SysexMessage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CENGFACTORYDB implements ICENGFACTORYDB{
    /**
     * Place your initialization code inside if required.
     *
     * <p>
     * This function will be called before all other operations. If your implementation
     * need initialization , necessary operations should be done inside this function. For
     * example, you can set your connection to the database server inside this function.
     */
    private java.sql.Connection connection;
    public void initialize() {
        connection = null;
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            connection = java.sql.DriverManager.getConnection("jdbc:mysql://144.122.71.128:8080/db2580843", "e2580843", "8bo%JpIVkGMg");
        }
        catch(java.sql.SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Should create the necessary tables when called.
     *
     * @return the number of tables that are created successfully.
     */
    public int createTables() {
        int i = 0;
        String arr[] = {
                """
                CREATE TABLE Factory(
                factoryId INT,
                factoryName TEXT,
                factoryType Text,
                country TEXT,
                PRIMARY KEY (factoryId));
                """,
                """
                CREATE TABLE Employee(
                employeeId int,
                employeeName Text,
                department Text,
                salary int,
                PRIMARY KEY (employeeId));
                """,
                """
                CREATE TABLE Works_In(
                factoryId int,
                employeeId int,
                startDate Date,
                PRIMARY KEY (factoryId, employeeId),
                FOREIGN KEY (factoryId) REFERENCES Factory ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (employeeId) REFERENCES Employee ON DELETE CASCADE ON UPDATE CASCADE);
                """,
                """
                CREATE TABLE Product
                (productId int,
                productName Text,
                productType Text,
                PRIMARY KEY (productId));
                """,
                """
                CREATE TABLE Produce(
                factoryId int,
                productId int,
                amount int,
                productionCost int,
                PRIMARY KEY(factoryId, productId),
                FOREIGN KEY (factoryId) REFERENCES Factory ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (productId) REFERENCES Product ON DELETE CASCADE ON UPDATE CASCADE);
                """,
                """
                CREATE TABLE Shipment(
                factoryId int,
                productId int,
                amount int,
                pricePerUnit int,
                PRIMARY KEY (factoryId, productId),
                FOREIGN KEY (factoryId) REFERENCES Factory ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (productId) REFERENCES Produce ON DELETE CASCADE ON UPDATE CASCADE);
                """};
        for(int j = 0; j < 6; j++ ){
            try{
                java.sql.Statement statement = connection.createStatement();
                statement.executeUpdate(arr[i]);
                i++;
                statement.close();
            }catch (java.sql.SQLException e){
                e.printStackTrace();
            }
        }
        //System.out.println(i);
        return i;
    }

    /**
     * Should drop the tables if exists when called.
     *
     * @return the number of tables are dropped successfully.
     */
    public int dropTables() {
        int i = 0;
            String arr[] = {
                    "DROP TABLE IF EXISTS Shipment",
                    "DROP TABLE IF EXISTS Produce",
                    "DROP TABLE IF EXISTS Works_In",
                    "DROP TABLE IF EXISTS Product",
                    "DROP TABLE IF EXISTS Employee",
                    "DROP TABLE IF EXISTS Factory"
            };
            for(int j = 0; j < 6; j++ ){
                try{
                    java.sql.Statement statement = connection.createStatement();
                    statement.executeUpdate(arr[i]);
                    i++;
                    statement.close();
                }catch (java.sql.SQLException e){
                    e.printStackTrace();
                }
            }
        //System.out.println(i);
        return i;
    }

    /**
     * Should insert an array of Factory into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertFactory(Factory[] factories) {
        int i = 0;
        for(Factory element : factories){
            try{
                java.sql.Statement statement = connection.createStatement();
                i += statement.executeUpdate("INSERT INTO Factory (factoryId, factoryName, factoryType, country) VALUES (" + element.getFactoryId() +  ",'" +
                        element.getFactoryName() + "','" + element.getFactoryType() + "','" + element.getCountry() + "');");
            }catch (java.sql.SQLException e){
                e.printStackTrace();
            }
        }

        //System.out.println("from factory insertion: " + i);
        return i;
    }

    /**
     * Should insert an array of Employee into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertEmployee(Employee[] employees) {
        int i = 0;

        for(Employee element : employees) {
            try {
                java.sql.Statement statement = connection.createStatement();
                i += statement.executeUpdate("INSERT INTO Employee (employeeId, employeeName, department, salary) VALUES (" + element.getEmployeeId() + ",'" +
                        element.getEmployeeName() + "','" + element.getDepartment() + "'," + element.getSalary() + ");");
                statement.close();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }

        }
        //System.out.println("from employee insertion:" + i);
        return i;
    }

    /**
     * Should insert an array of WorksIn into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertWorksIn(WorksIn[] worksIns) {
        int i = 0;
        for (WorksIn element : worksIns) {
            try {
                java.sql.Statement statement = connection.createStatement();
                i += statement.executeUpdate("INSERT INTO Works_In (factoryId, employeeId, startDate) VALUES (" + element.getFactoryId() + "," +
                        element.getEmployeeId() + ",'" + element.getStartDate() + "');");
                statement.close();
            }catch(java.sql.SQLException e){
                e.printStackTrace();
            }
        }
        //System.out.println("from works_in insertion:" + i);
        return i;
    }

    /**
     * Should insert an array of Product into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertProduct(Product[] products) {
        int i = 0;
        for(Product element: products) {
            try {
                java.sql.Statement statement = connection.createStatement();
                i += statement.executeUpdate("INSERT INTO Product (productId, productName, productType) VALUES (" + element.getProductId() + ",'" +
                        element.getProductName() + "','" + element.getProductType() + "');");
                statement.close();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("from product insertion:" + i);
        return i;
    }


    /**
     * Should insert an array of Produce into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertProduce(Produce[] produces) {
        int i = 0;
        for(Produce element: produces) {
            try {
                java.sql.Statement statement = connection.createStatement();
                i += statement.executeUpdate("INSERT INTO Produce (factoryId, productId, amount, productionCost) VALUES (" + element.getFactoryId() + "," +
                        element.getProductId() + "," + element.getAmount() + "," + element.getProductionCost() + ");");
                statement.close();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
       // System.out.println("from produce insertion:" + i);
        return i;
    }


    /**
     * Should insert an array of Shipment into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertShipment(Shipment[] shipments) {
        int i = 0;
        for(Shipment element: shipments) {
            try {
                java.sql.Statement statement = connection.createStatement();
                i += statement.executeUpdate("INSERT INTO Shipment (factoryId, productId, amount, pricePerUnit) VALUES (" + element.getFactoryId() + "," +
                        element.getProductId() + "," + element.getAmount() + "," + element.getPricePerUnit() + ");");
                statement.close();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("from shipment insertion:" + i);
        return i;
    }

    /**
     * Should return all factories that are located in a particular country.
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesForGivenCountry(String country) {
        java.util.ArrayList<Factory> arrayList = new ArrayList<>();
        Factory[] factoryArr = null;
        try{
            String query = """
                    SELECT DISTINCT*
                    FROM Factory F
                    WHERE F.country = ?
                    ORDER BY F.factoryId ASC;
                    """;
            java.sql.PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,country);
            java.sql.ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                //take values of each row seperately
                int factoryId = resultSet.getInt(1);
                String factoryName = resultSet.getString(2);
                String factoryType = resultSet.getString(3);
                String factoryCountry = resultSet.getString(4);
                Factory factory = new Factory(factoryId, factoryName, factoryType, factoryCountry);
                arrayList.add(factory);
            }
            //System.out.println("List size is: " + arrayList.size());
            factoryArr = new Factory[arrayList.size()];
            arrayList.toArray(factoryArr);
        }catch(java.sql.SQLException e){
            e.printStackTrace();
        }
        //System.out.println("Array size is: " + factoryArr.length);
        //System.out.println("First factory in the list is: " + factoryArr[0]);
        return factoryArr;
    }



    /**
     * Should return all factories without any working employees.
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesWithoutAnyEmployee() {
        java.util.ArrayList<Factory> arrayList = new ArrayList<>();
        Factory[] factoryArr = null;
        try{
            java.sql.Statement statement = connection.createStatement();
            java.sql.ResultSet resultSet = statement.executeQuery("""
                    SELECT DISTINCT*
                    FROM Factory F
                    WHERE F.factoryId NOT IN(
                                 SELECT DISTINCT W.factoryId
                                 FROM Works_In W
                                )
                    ORDER BY F.factoryId ASC;
                    """);
            while(resultSet.next()){
                //take values of each row seperately
                int factoryId = resultSet.getInt(1);
                String factoryName = resultSet.getString(2);
                String factoryType = resultSet.getString(3);
                String factoryCountry = resultSet.getString(4);
                Factory factory = new Factory(factoryId, factoryName, factoryType, factoryCountry);
                arrayList.add(factory);
            }
            //System.out.println("Factory with no employee. List size is: " + arrayList.size());
            factoryArr = new Factory[arrayList.size()];
            arrayList.toArray(factoryArr);
        }catch (java.sql.SQLException e){
            e.printStackTrace();
        }
        //System.out.println("First factory with no employee: " + factoryArr[0]);
        return factoryArr;
    }

    /**
     * Should return all factories that produce all products for a particular productType
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesProducingAllForGivenType(String productType) {
        //System.out.println("Product Type is: " + productType);
        java.util.ArrayList<Factory> arrayList = new ArrayList<>();
        Factory[] factoryArr = null;
        try{
            String query = """
                                        SELECT DISTINCT *
                                        FROM Factory F
                                        WHERE NOT EXISTS(
                                                     SELECT P1.productId
                                                     FROM Product P1
                                                     WHERE P1.productType = ?
                                        
                                                     EXCEPT
                                                     SELECT PR.productId
                                                     FROM Produce PR
                                                     WHERE PR.factoryId = F.factoryId AND PR.productId IN (  SELECT P2.productId
                                                                                                            FROM Product P2
                                                                                                            WHERE P2.productType = ?
                                                                                                          )
                                                    )
                                        ORDER BY F.factoryId ASC;
                    """;
            java.sql.PreparedStatement statement = connection.prepareStatement(query); /* we are required to use that since we will pass a variable to  the query. */
            statement.setString(1, productType);
            statement.setString(2, productType);
            java.sql.ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                //take values of each row seperately
                int factoryId = resultSet.getInt(1);
                String factoryName = resultSet.getString(2);
                String factoryType = resultSet.getString(3);
                String factoryCountry = resultSet.getString(4);
                Factory factory = new Factory(factoryId, factoryName, factoryType, factoryCountry);
                arrayList.add(factory);
            }
            //System.out.println("Factory produces all products of that type. List size is: " + arrayList.size());
            factoryArr = new Factory[arrayList.size()];
            arrayList.toArray(factoryArr);
        }catch (java.sql.SQLException e){
            e.printStackTrace();
        }
        //System.out.println("Printing array content: --------------------------------------------------");
        /*for(int i = 0; i < factoryArr.length; i++){
            System.out.println(factoryArr[i]);
        }
        */

        return factoryArr;
    }


    /**
     * Should return the products that are produced in a particular factory but
     * don’t have any shipment from that factory.
     *
     * @return Product[]
     */
    public Product[] getProductsProducedNotShipped() {
        java.util.ArrayList<Product> arrayList = new ArrayList<>();
        Product[] productArr = null;
        try{
            String query = """
                           SELECT DISTINCT P.productId, P.productName, P.productType
                           FROM Product P, Produce PR
                           WHERE P.productId = PR.productId AND NOT EXISTS (
                                          SELECT S.factoryId
                                          FROM Shipment S
                                          WHERE S.factoryId = PR.factoryId AND S.productId = P.productId
                                          
                                        )
                           ORDER BY P.productId ASC;
                    """;
            java.sql.PreparedStatement statement = connection.prepareStatement(query);

            java.sql.ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                //take values of each row seperately
                int productId = resultSet.getInt(1);
                String productName = resultSet.getString(2);
                String productType = resultSet.getString(3);
                Product product = new Product(productId, productName, productType);
                arrayList.add(product);
            }
            //System.out.println("Product that is not shipped from factory it is produced. List size is: " + arrayList.size());
            productArr = new Product[arrayList.size()];
            arrayList.toArray(productArr);
        }catch (java.sql.SQLException e){
            e.printStackTrace();
        }
        return productArr;
    }


    /**
     * For a given factoryId and department, should return the average salary of
     *     the employees working in that factory and that specific department.
     *
     * @return double
     */
    public double getAverageSalaryForFactoryDepartment(int factoryId, String department) {
        double avgValue = 0.0;
        try{
            String query = """
                           SELECT AVG(E.salary)
                           FROM Works_In W, Employee E
                           WHERE W.employeeId = E.employeeId AND W.factoryId = ? AND E.department = ?;
                    """;
            java.sql.PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, factoryId);
            statement.setString(2, department);
            java.sql.ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            avgValue = resultSet.getDouble(1);

        }catch (java.sql.SQLException e){
            e.printStackTrace();
        }
        //System.out.println("Average salary value: "+ avgValue);
        return avgValue;
    }


    /**
     * Should return the most profitable products for each factory
     *
     * @return QueryResult.MostValueableProduct[]
     */
    public QueryResult.MostValueableProduct[] getMostValueableProducts() {
        java.util.ArrayList<QueryResult.MostValueableProduct> arrayList = new ArrayList<>();
        QueryResult.MostValueableProduct[] productArr = null;
        try{
            String query = """
                           SELECT DISTINCT F.factoryId, P1.productId, P1.productName, P1.productType,
                                           COALESCE( (S1.amount * S1.pricePerUnit - PR1.amount * PR1.productionCost), 0 - PR1.amount * PR1.productionCost) AS profit
                           FROM Factory F
                           JOIN Product P1
                           JOIN Produce PR1 ON PR1.productId = P1.productId AND F.factoryId = PR1.factoryId
                           LEFT JOIN Shipment S1 ON S1.factoryId = F.factoryId AND S1.productId = P1.productId
                           WHERE P1.productId IN (
                                   SELECT P2.productId
                                   FROM Product P2
                                   JOIN Produce PR2 ON PR2.productId = P2.productId AND PR2.factoryId = F.factoryId
                                   LEFT JOIN Shipment S2 ON P2.productId = S2.productId AND S2.factoryId = F.factoryId
                                   WHERE COALESCE( S2.amount * S2.pricePerUnit - PR2.amount * PR2.productionCost, 0 - PR2.amount * PR2.productionCost) = 
                                        (
                                           SELECT MAX( COALESCE(S3.amount * S3.pricePerUnit - PR3.amount * PR3.productionCost, 0 - PR3.amount * PR3.productionCost))
                                           FROM Produce PR3
                                           LEFT JOIN Shipment S3 ON S3.factoryId = F.factoryId AND S3.productId = PR3.productId
                                           WHERE PR3.factoryId = F.factoryId
                                       )
                                   )
                           ORDER BY
                               profit DESC, P1.productId ASC;
                           
                    """;

            java.sql.PreparedStatement statement = connection.prepareStatement(query);

            java.sql.ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                //take values of each row seperately
                int factoryId = resultSet.getInt(1);
                int productId = resultSet.getInt(2);
                String productName = resultSet.getString(3);
                String productType = resultSet.getString(4);
                double profit = resultSet.getDouble(5);
                QueryResult.MostValueableProduct obj = new QueryResult.MostValueableProduct(factoryId, productId, productName, productType, profit);
                arrayList.add(obj);
            }
            //System.out.println("Most valueable product per each factory. List size is: " + arrayList.size());
            productArr = new QueryResult.MostValueableProduct[arrayList.size()];
            arrayList.toArray(productArr);
        }catch (java.sql.SQLException e){
            e.printStackTrace();
        }
        /*
        System.out.println("Printing array content: --------------------------------------------------");
        for(int i = 0; i < productArr.length; i++){
            System.out.println(productArr[i]);
        }
        System.out.println("Length of array:" + productArr.length);


         */


        return productArr;
    }


    /**
     * For each product, return the factories that gather the highest profit
     * for that product
     *
     * @return QueryResult.MostValueableProduct[]
     */
    public QueryResult.MostValueableProduct[] getMostValueableProductsOnFactory() {
        java.util.ArrayList<QueryResult.MostValueableProduct> arrayList = new ArrayList<>();
        QueryResult.MostValueableProduct[] productArr = null;
        try{
            String query =  """
                           SELECT DISTINCT F.factoryId, P1.productId, P1.productName, P1.productType,
                                           COALESCE( (S1.amount * S1.pricePerUnit - PR1.amount * PR1.productionCost), 0 - PR1.amount * PR1.productionCost) AS profit
                           FROM Factory F
                           JOIN Product P1
                           JOIN Produce PR1 ON PR1.productId = P1.productId AND F.factoryId = PR1.factoryId
                           LEFT JOIN Shipment S1 ON S1.factoryId = F.factoryId AND S1.productId = P1.productId
                           WHERE F.factoryId IN (
                                   SELECT F2.factoryId
                                   FROM Factory F2
                                   JOIN Produce PR2 ON PR2.productId = P1.productId AND PR2.factoryId = F2.factoryId
                                   LEFT JOIN Shipment S2 ON P1.productId = S2.productId AND S2.factoryId = F.factoryId
                                   WHERE COALESCE( S2.amount * S2.pricePerUnit - PR2.amount * PR2.productionCost, 0 - PR2.amount * PR2.productionCost) = 
                                        (
                                           SELECT MAX( COALESCE(S3.amount * S3.pricePerUnit - PR3.amount * PR3.productionCost, 0 - PR3.amount * PR3.productionCost))
                                           FROM Produce PR3
                                           LEFT JOIN Shipment S3 ON S3.productId = P1.productId AND PR3.factoryId = S3.factoryId
                                           WHERE P1.productId = PR3.productId
                                       )
                                   )
                           ORDER BY
                               profit DESC, P1.productId ASC;
                           
                    """;

            java.sql.PreparedStatement statement = connection.prepareStatement(query);

            java.sql.ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                //take values of each row seperately
                int factoryId = resultSet.getInt(1);
                int productId = resultSet.getInt(2);
                String productName = resultSet.getString(3);
                String productType = resultSet.getString(4);
                double profit = resultSet.getDouble(5);
                QueryResult.MostValueableProduct obj = new QueryResult.MostValueableProduct(factoryId, productId, productName, productType, profit);
                arrayList.add(obj);
            }
            //System.out.println("Highest profit gainer factories per each product. List size is: " + arrayList.size());
            productArr = new QueryResult.MostValueableProduct[arrayList.size()];
            arrayList.toArray(productArr);
        }catch (java.sql.SQLException e){
            e.printStackTrace();
        }
        /*
        System.out.println("Printing array content: --------------------------------------------------");
        for(int i = 0; i < productArr.length; i++){
            System.out.println(productArr[i]);
        }

         */

        return productArr;
    }


    /**
     * For each department, should return all employees that are paid under the
     *     average salary for that department. You consider the employees
     *     that do not work earning ”0”.
     *
     * @return QueryResult.LowSalaryEmployees[]
     */
    public QueryResult.LowSalaryEmployees[] getLowSalaryEmployeesForDepartments() {
        java.util.ArrayList<QueryResult.LowSalaryEmployees> arrayList = new ArrayList<>();
        QueryResult.LowSalaryEmployees[] arr = null;
        try{
            String query = """
                          SELECT DISTINCT *
                          FROM Employee E
                          WHERE E.salary < (
                                            (SELECT SUM(E2.salary)
                                            FROM Employee E2
                                            WHERE E2.department = E.department)
                                            /
                                            (SELECT COUNT(*)
                                             FROM Employee E1
                                             WHERE E1.department = E.department) 
                                            )
                          UNION
                          
                          SELECT DISTINCT E3.employeeId, E3.employeeName, E3.department, 0
                          FROM Employee E3
                          WHERE E3.employeeId NOT IN(
                                                    SELECT W.employeeId
                                                    FROM Works_In W
                                                    )              
                          ORDER BY employeeId ASC;
                    """;
            java.sql.PreparedStatement statement = connection.prepareStatement(query);

            java.sql.ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                //take values of each row seperately
                int employeeId = resultSet.getInt(1);
                String employeeName = resultSet.getString(2);
                String department = resultSet.getString(3);
                int salary = resultSet.getInt(4);
                QueryResult.LowSalaryEmployees obj = new QueryResult.LowSalaryEmployees(employeeId, employeeName, department, salary);
                arrayList.add(obj);
            }
            //System.out.println("Under paid employees per each department. List size is: " + arrayList.size());
            arr = new QueryResult.LowSalaryEmployees[arrayList.size()];
            arrayList.toArray(arr);
        }catch (java.sql.SQLException e){
            e.printStackTrace();
        }
        return arr;
    }


    /**
     * For the products of given productType, increase the productionCost of every unit by a given percentage.
     *
     * @return number of rows affected
     */
    public int increaseCost(String productType, double percentage) {
        int row_count = 0;
        //System.out.println("given percentage as argument: " + percentage);
        try{
            String queryUpdating = """
                    UPDATE Produce PR
                    SET PR.productionCost = (1 + ?) * PR.productionCost
                    WHERE PR.productId IN (
                                            SELECT P.productId
                                            FROM Product P
                                            WHERE P.productType = ?
                                          )
                    """;
            java.sql.PreparedStatement updateStatement = connection.prepareStatement(queryUpdating);
            updateStatement.setDouble(1, percentage);
            updateStatement.setString(2, productType);
            row_count = updateStatement.executeUpdate();
            //System.out.println("Number of rows that productionCost is changed: " + row_count);
        }catch (java.sql.SQLException e){
            e.printStackTrace();
        }
        return row_count;
    }


    /**
     * Deleting all employees that have not worked since the given date.
     *
     * @return number of rows affected
     */
    public int deleteNotWorkingEmployees(String givenDate) {
        //find all the employees that are started earlier than givenDate
        int row_count = 0;
        //System.out.println("givenDate as argument: " + givenDate);
        try{
            String queryUpdating = """
                          DELETE
                          FROM Employee
                          WHERE employeeId NOT IN (
                                                    SELECT W.employeeId
                                                    FROM Works_In W
                                                    WHERE W.startDate > ?
                          )
                    """;
            java.sql.PreparedStatement updateStatement= connection.prepareStatement(queryUpdating);
            updateStatement.setString(1, givenDate);
            row_count = updateStatement.executeUpdate();
            //System.out.println("Number of employees dropped from Employee: " + row_count);
        }catch (java.sql.SQLException e){
            e.printStackTrace();
        }
        return row_count;
    }


    /**
     * *****************************************************
     * *****************************************************
     * *****************************************************
     * *****************************************************
     *  THE METHODS AFTER THIS LINE WILL NOT BE GRADED.
     *  YOU DON'T HAVE TO SOLVE THEM, LEAVE THEM AS IS IF YOU WANT.
     *  IF YOU HAVE ANY QUESTIONS, REACH ME VIA EMAIL.
     * *****************************************************
     * *****************************************************
     * *****************************************************
     * *****************************************************
     */

    /**
     * For each department, find the rank of the employees in terms of
     * salary. Peers are considered tied and receive the same rank. After
     * that, there should be a gap.
     *
     * @return QueryResult.EmployeeRank[]
     */
    public QueryResult.EmployeeRank[] calculateRank() {
        return new QueryResult.EmployeeRank[0];
    }

    /**
     * For each department, find the rank of the employees in terms of
     * salary. Everything is the same but after ties, there should be no
     * gap.
     *
     * @return QueryResult.EmployeeRank[]
     */
    public QueryResult.EmployeeRank[] calculateRank2() {
        return new QueryResult.EmployeeRank[0];
    }

    /**
     * For each factory, calculate the most profitable 4th product.
     *
     * @return QueryResult.FactoryProfit
     */
    public QueryResult.FactoryProfit calculateFourth() {
        return new QueryResult.FactoryProfit(0,0,0);
    }

    /**
     * Determine the salary variance between an employee and another
     * one who began working immediately after the first employee (by
     * startDate), for all employees.
     *
     * @return QueryResult.SalaryVariant[]
     */
    public QueryResult.SalaryVariant[] calculateVariance() {
        return new QueryResult.SalaryVariant[0];
    }

    /**
     * Create a method that is called once and whenever a Product starts
     * losing money, deletes it from Produce table
     *
     * @return void
     */
    public void deleteLosing() {

    }
}
