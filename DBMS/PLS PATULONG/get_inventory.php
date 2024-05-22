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

$sql = "SELECT * FROM products";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    echo "<thead><tr><th>ID</th><th>Name</th><th>Stock</th><th>Price</th></tr></thead><tbody>";
    while($row = $result->fetch_assoc()) {
        echo "<tr><td>" . $row["id"] . "</td><td>" . $row["name"] . "</td><td>" . $row["stock"] . "</td><td>PHP " . number_format($row["price"], 2) . "</td></tr>";
    }
    echo "</tbody>";
} else {
    echo "<tr><td colspan='4'>0 results</td></tr>";
}
$conn->close();
?>
