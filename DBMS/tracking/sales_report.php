<?php

include("connection.php");

// Function to get total sales for a specific date
function getTotalSales($date, $conn) {
    $sql = "SELECT SUM(total_sales) AS total FROM sales WHERE date = '$date'";
    $result = $conn->query($sql);
    $row = $result->fetch_assoc();
    return $row['total'] ? $row['total'] : 0;
}

// Get current date
$current_date = date("Y-m-d");

// Get yesterday's date
$yesterday_date = date("Y-m-d", strtotime("-1 day"));

// Get selected date from the form
if (isset($_POST['selected_date'])) {
    $selected_date = $_POST['selected_date'];
} else {
    // Default to today's date if no date is selected
    $selected_date = $current_date;
}

// Get total sales for the selected date
$total_sales_selected = getTotalSales($selected_date, $conn);

// Get total sales for yesterday
$total_sales_yesterday = getTotalSales($yesterday_date, $conn);

// Get list of products with low stock
$low_stock_products = [];
$sql_low_stock = "SELECT name FROM products WHERE stock < 10";
$result_low_stock = $conn->query($sql_low_stock);
while ($row_low_stock = $result_low_stock->fetch_assoc()) {
    $low_stock_products[] = $row_low_stock['name'];
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sales Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f8f8;
            color: #333;
            margin: 0;
            padding: 0;
        }
        .container {
            margin: 20px auto;
            width: 80%;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #c31a44;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #c31a44;
            color: #fff;
        }
        .notification {
            background-color: #f2dede;
            border-color: #ebccd1;
            color: #a94442;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
        }
        .notification p {
            margin: 0;
        }
        .notification ul {
            margin-top: 5px;
            padding-left: 20px;
        }
        .notification li {
            margin-bottom: 5px;
        }
        label {
            font-weight: bold;
        }
        input[type="date"] {
            padding: 8px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        button[type="submit"] {
            padding: 8px 15px;
            background-color: #c31a44;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button[type="submit"]:hover {
            background-color: #a41839;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Sales Report</h2>
        <form method="POST">
            <label for="selected_date">Select Date:</label>
            <input type="date" id="selected_date" name="selected_date" value="<?php echo $selected_date; ?>">
            <button type="submit">View Sales</button>
        </form>
        
        <table>
            <tr>
                <th>Date</th>
                <th>Total Sales</th>
            </tr>
            <tr>
                <td><?php echo $selected_date; ?></td>
                <td><?php echo $total_sales_selected; ?></td>
            </tr>
            <tr>
                <td><?php echo $yesterday_date; ?></td>
                <td><?php echo $total_sales_yesterday; ?></td>
            </tr>
        </table>

        <?php if (!empty($low_stock_products)) { ?>
            <div class="notification">
                <p>Products with Low Stock:</p>
                <ul>
                    <?php foreach ($low_stock_products as $product) { ?>
                        <li><?php echo $product; ?></li>
                    <?php } ?>
                </ul>
            </div>
        <?php } ?>
    </div>
</body>
</html>

<?php
$conn->close();
?>
