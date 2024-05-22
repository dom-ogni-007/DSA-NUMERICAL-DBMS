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

// Delete product
$id = $_GET['id'];
$sql = "DELETE FROM products WHERE id=$id";

if ($conn->query($sql) === TRUE) {
    echo "Product deleted successfully";
} else {
    echo "Error deleting product: " . $conn->error;
}

$conn->close();
?>
