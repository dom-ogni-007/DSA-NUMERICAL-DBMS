<?php
// Database connection
$servername = "localhost";
$username = "root";
$password = "134679825";
$dbname = "inventory0";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Update product details
$id = $_POST['id'];
$name = $_POST['name'];
$quantity = $_POST['quantity'];
$stock = $_POST['stock'];
$price = $_POST['price'];

$sql = "UPDATE products SET name='$name', quantity=$quantity, stock=$stock, price=$price WHERE id=$id";

if ($conn->query($sql) === TRUE) {
    echo "Product updated successfully";
} else {
    echo "Error updating product: " . $conn->error;
}

$conn->close();
?>
