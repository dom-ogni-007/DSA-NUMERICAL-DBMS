<?php
// Database connection (replace with your actual credentials)
$servername = "localhost";
$username = "root";
$password = "134679825";
$dbname = "inventory0";

// Escape user input to prevent SQL injection (IMPORTANT!)
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

// Check if form is submitted
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Retrieve form data
    $name = $_POST['name'];
    $quantity = intval($_POST['quantity']);  // Cast to integer
    $stock = intval($_POST['stock']);        // Cast to integer
    $price = floatval($_POST['price']);      // Cast to float

    // Prepare statement for better security and reusability
    $sql = "INSERT INTO products (name, quantity, stock, price) VALUES (?, ?, ?, ?)";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sidi", $name, $quantity, $stock, $price);

    if ($stmt->execute() === TRUE) {
      echo "New product added successfully";
    } else {
      echo "Error adding product: " . $conn->error;
    }

    $stmt->close();
}

$conn->close();
?>
