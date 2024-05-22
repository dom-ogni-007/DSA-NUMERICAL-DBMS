$(document).ready(function() {
    // Load inventory table on page load
    loadInventory();

    // Add Product Button Click Event
    $("#add-product-btn").click(function() {
        $("#add-product-form").toggle();
    });

    // Add Product Button Click Event
    $("#add-btn").click(function() {
        var name = $("#name").val();
        var stock = $("#stock").val();
        var price = $("#price").val();

        $.ajax({
            url: "add_product.php",
            type: "POST",
            data: { name: name, stock: stock, price: price },
            success: function(response) {
                loadInventory();
                $("#name, #stock, #price").val("");
            }
        });
    });

    // Function to load inventory table
    function loadInventory() {
        $.ajax({
            url: "get_inventory.php",
            type: "GET",
            success: function(response) {
                $("#inventory-table").html(response);
            }
        });
    }
});