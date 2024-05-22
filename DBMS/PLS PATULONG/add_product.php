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

$name = $_POST['name'];
$stock = $_POST['stock'];
$price = $_POST['price'];

$sql = "INSERT INTO products (name, stock, price) VALUES ('$name', '$stock', '$price')";

if ($conn->query($sql) === TRUE) {
    echo "Product added successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>
