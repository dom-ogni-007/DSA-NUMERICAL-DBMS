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

// Check if form is submitted
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  // Retrieve form data
  $name = $_POST['name'];
  $quantity = intval($_POST['quantity']);  // Cast to integer
  $stock = intval($_POST['stock']);        // Cast to integer
  $price = floatval($_POST['price']);        // Cast to float

  // Prepare statement for adding product
  $sql = "INSERT INTO products (name, quantity, stock, price) VALUES (?, ?, ?, ?)";
  $stmt = $conn->prepare($sql);
  $stmt->bind_param("sidi", $name, $quantity, $stock, $price);

  if ($stmt->execute() === TRUE) {
    echo "New product added successfully!";
  } else {
    echo "Error adding product: " . $conn->error;
  }

  $stmt->close();
}

// Fetch products from the database
$sql = "SELECT * FROM products";
$result = $conn->query($sql);

?>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Inventory</title>
</head>
<body>
  <h2>Inventory</h2>

  <br>
  <form action="" method="post">
    <h2>Add Product</h2>
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name" required><br>
    <label for="quantity">Quantity:</label><br>
    <input type="number" id="quantity" name="quantity" required><br>
    <label for="stock">Stock:</label><br>
    <input type="number" id="stock" name="stock" required><br>
    <label for="price">Price:</label><br>
    <input type="number" step="0.01" id="price" name="price" required><br>
    <br><br>
    <input type="submit" value="Add">
  </form>

  <br>
  <table border="1">
    <tr>
      <th>ID</th>
      <th>Name</th>
      <th>Quantity</th>
      <th>Stock</th>
      <th>Price</th>
      <th>Action</th>
    </tr>
    <?php
    if ($result->num_rows > 0) {
      while ($row = $result->fetch_assoc()) {
        echo "<tr>";
        echo "<td>" . $row['id'] . "</td>";
        echo "<td>" . $row['name'] . "</td>";
        echo "<td>" . $row['quantity'] . "</td>";
        echo "<td>" . $row['stock'] . "</td>";
        echo "<td>" . $row['price'] . "</td>";
        echo "<td><a href='edit.php?id=" . $row['id'] . "'>Edit</a> | <a href='delete.php?id=" . $row['id'] . "'>Delete</a></td>";
        echo "</tr>";
      }
    } else {
      echo "<tr><td colspan='6'>No products found</td></tr>";
    }
    ?>
  </table>

  <?php
  $conn->close();
  ?>
</body>
</html>