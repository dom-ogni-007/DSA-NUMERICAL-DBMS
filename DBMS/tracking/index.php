<?php

include("connection.php");

// Initialize cart
$cart = isset($_POST['cart']) ? json_decode($_POST['cart'], true) : [];
$total_amount = 0;
$total_products = 0;
$search_results = [];

// Handle product search
if (isset($_POST['search_term'])) {
    $search_term = $conn->real_escape_string($_POST['search_term']);
    $sql = "SELECT id, name, price, stock 
            FROM products 
            WHERE name LIKE '%$search_term%'";
    $search_results = $conn->query($sql);
}

// Handle adding product to cart
if (isset($_POST['product_id'])) {
    $product_id = intval($_POST['product_id']);
    $quantity = intval($_POST['quantity']);

    $sql = "SELECT id, name, price, stock 
            FROM products 
            WHERE id = $product_id";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $product = $result->fetch_assoc();

        if ($product['stock'] >= $quantity) {
            $product['quantity'] = $quantity;
            $product['total_price'] = $quantity * $product['price'];
            $cart[] = $product;
            
            $new_stock = $product['stock'] - $quantity;
            $update_stock_sql = "UPDATE products SET stock = $new_stock WHERE id = $product_id";
            $conn->query($update_stock_sql);

            // Update total sales in the sales table
            $date = date("Y-m-d");
            $total_price = $product['total_price']; // Added this line to store total price in a variable
            $update_sales_sql = "INSERT INTO sales (date, total_sales) VALUES ('$date', $total_price) ON DUPLICATE KEY UPDATE total_sales = total_sales + $total_price";
            $conn->query($update_sales_sql);
        } else {
            echo "Not enough stock for " . $product['name'];
        }
    } else {
        echo "Product not found.";
    }
}

foreach ($cart as $item) {
    $total_amount += $item['total_price'];
    $total_products += $item['quantity'];
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>POS System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .header {
            background-color: #c31a44;
            color: #fff;
            padding: 15px;
            text-align: center;
            position: fixed;
            width: 100%;
            top: 0;
            left: 0;
            z-index: 1000;
        }
        .header form {
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .header input[type="text"] {
            padding: 10px;
            margin-right: 10px;
            border: none;
            border-radius: 5px;
        }
        .header button {
            padding: 10px 20px;
            background-color: #1AC399;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .header button:hover {
            background-color: #4CAF50;
        }
        .container {
            margin-top: 80px;
            padding: 20px;
        }
        .search-results, .cart {
            background-color: #fff;
            padding: 20px;
            margin-bottom: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #c31a44;
            color: white; /* Make column names white */
        }
        .cart-table th, .cart-table td {
            text-align: center;
        }
        .cart-table tfoot {
            font-weight: bold;
        }
        .checkout-button {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #1AC399;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
        }
        .checkout-button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<div class="header">
    <form method="POST">
        <label for="search_term" style="margin-right: 10px; font-size: 18px;">Search Product:</label>
        <input type="text" id="search_term" name="search_term" required>
        <input type="hidden" name="cart" value='<?php echo json_encode($cart); ?>'>
        <button type="submit">Search</button>
    </form>
</div>

<div class="container">
    <?php if (!empty($search_results)) { ?>
        <div class="search-results">
            <h2>Search Results</h2>
            <table>
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Price</th>
                        <th>Stock</th>
                        <th>Quantity</th>
                        <th>Add to Cart</th>
                    </tr>
                </thead>
                <tbody>
                    <?php while($row = $search_results->fetch_assoc()) { ?>
                        <tr>
                            <td><?php echo $row['name']; ?></td>
                            <td><?php echo $row['price']; ?></td>
                            <td><?php echo $row['stock']; ?></td>
                            <td>
                                <form method="POST">
                                    <input type="number" name="quantity" min="1" max="<?php echo $row['stock']; ?>" required>
                                    <input type="hidden" name="product_id" value="<?php echo $row['id']; ?>">
                                    <input type="hidden" name="cart" value='<?php echo json_encode($cart); ?>'>
                                    <button type="submit">Add</button>
                                </form>
                            </td>
                        </tr>
                    <?php } ?>
                </tbody>
            </table>
        </div>
    <?php } ?>

    <?php if (!empty($cart)) { ?>
        <div class="cart">
            <h2>Cart</h2>
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Quantity</th>
                        <th>Price per Unit</th>
                        <th>Total Price</th>
                    </tr>
                </thead>
                <tbody>
                    <?php foreach ($cart as $item) { ?>
                        <tr>
                            <td><?php echo $item['name']; ?></td>
                            <td><?php echo $item['quantity']; ?></td>
                            <td><?php echo $item['price']; ?></td>
                            <td><?php echo $item['total_price']; ?></td>
                        </tr>
                    <?php } ?>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="3">Total Products</td>
                        <td><?php echo $total_products; ?></td>
                    </tr>
                    <tr>
                        <td colspan="3">Total Amount</td>
                        <td><?php echo $total_amount; ?></td>
                    </tr>
                </tfoot>
            </table>
            <form method="POST">
                <input type="hidden" name="cart" value='[]'>
                <button type="submit" class="checkout-button">Checkout</button>
            </form>
        </div>
    <?php } ?>
</div>

</body>
</html>

<?php
$conn->close();
?>
