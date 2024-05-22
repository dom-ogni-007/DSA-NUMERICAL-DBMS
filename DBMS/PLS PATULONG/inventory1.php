<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventory Page</title>
    <link rel="stylesheet" href="inventory1.css">
</head>
<body>
    <div class="sidebar">
        <ul>
            <li><a href="inventory1.php">Home</a></li>
            <li><a href="inventory.php">Inventory</a></li>
        </ul>
    </div>
    <div class="content">
        <div class="inventory">
            <?php
            $servername = "localhost";
            $username = "root";
            $password = "134679825";
            $dbname = "inventory_db";

            // Create connection
            $conn = new mysqli($servername, $username, $password, $dbname);

            // Check connection
            if ($conn->connect_error) {
                die("Connection failed: " . $conn->connect_error);
            }

            // Fetch products
            $sql = "SELECT * FROM products";
            $result = $conn->query($sql);

            if (!$result) {
                die("Query failed: " . $conn->error);
            }

            if ($result->num_rows > 0) {
                while($row = $result->fetch_assoc()) {
                    echo '<div class="product" data-id="' . $row["id"] . '">';
                    echo '<div class="edit-remove-buttons">';
                    echo '<button class="edit">&#9998;</button>';
                    echo '<button class="remove">&#10006;</button>';
                    echo '</div>';
                    echo '<div class="stock-counter">Stock: <span class="stock">' . $row["stock"] . '</span></div>';
                    echo '<div class="name">' . $row["name"] . '</div>';
                    echo '<div class="price">PHP ' . number_format($row["price"], 2) . '</div>';
                    echo '</div>';
                }
            } else {
                echo "0 results";
            }
            $conn->close();
            ?>
        </div>
    </div>
</body>
</html>
