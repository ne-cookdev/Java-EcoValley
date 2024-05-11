function addToCart(button) {
    const cartItemsElement = document.getElementById('cart-items');
    const productName = button.closest('.product').querySelector('.productName').textContent;
    const productCost = button.closest('.product').querySelector('.productCost').textContent;
    const existingCartItem = findCartItemByName(productName);
    
    if (existingCartItem)
    {
        // Увеличиваем количество товара на 1
        increaseQuantity(existingCartItem);
    }
    else
    {
        // Создаем новый элемент корзины
        const cartItemElement = document.createElement('li');
        cartItemElement.textContent = productName;
        cartItemElement.classList.add('cart-item');
        
        const quantityControls = document.createElement('div');
        quantityControls.classList.add('quantity-controls');
        
        const decreaseButton = document.createElement('button');
        decreaseButton.textContent = '-';
        decreaseButton.onclick = function() { decreaseQuantity(cartItemElement); };
        
        const quantitySpan = document.createElement('span');
        quantitySpan.textContent = '1';
        
        const increaseButton = document.createElement('button');
        increaseButton.textContent = '+';
        increaseButton.onclick = function() { increaseQuantity(cartItemElement); };
        
        quantityControls.appendChild(decreaseButton);
        quantityControls.appendChild(quantitySpan);
        quantityControls.appendChild(increaseButton);
        
        cartItemElement.appendChild(quantityControls);
        
        cartItemsElement.appendChild(cartItemElement);
    }
    
    saveCartToCookies();
}

function findCartItemByName(productName) {
    const cartItemsElements = document.querySelectorAll('.cart-item');
    for (const cartItemElement of cartItemsElements) {
        const name = cartItemElement.textContent.trim();
        console.log(name);
        console.log(productName);
        if (name.includes(productName)) {
            return cartItemElement;
        }
    }
    return null;
}

function increaseQuantity(cartItemElement) {
    const quantitySpan = cartItemElement.querySelector('.quantity-controls span');
    let quantity = parseInt(quantitySpan.textContent);
    quantity++;
    quantitySpan.textContent = quantity;
    saveCartToCookies();
}

function decreaseQuantity(cartItemElement) {
    const quantitySpan = cartItemElement.querySelector('.quantity-controls span');
    let quantity = parseInt(quantitySpan.textContent);
    if (quantity > 1) {
        quantity--;
        quantitySpan.textContent = quantity;
    } else {
        cartItemElement.remove(); // Удаляем элемент из DOM
    }
    saveCartToCookies();
}

function saveCartToCookies() {
    const cartItems = [];
    const cartItemsElements = document.querySelectorAll('.cart-item');
    cartItemsElements.forEach(item => {
        const productName = item.firstChild.textContent;
        const quantity = parseInt(item.querySelector('.quantity-controls span').textContent);
        if (quantity > 0) { // Добавляем только элементы с количеством больше 0
            cartItems.push({ productName, quantity });
        }
    });
    
    document.cookie = `cartItems=${JSON.stringify(cartItems)}; expires=${new Date(Date.now() + 86400000).toUTCString()}; path=/`;
}

  function loadCartFromCookies() {
    const cookies = document.cookie.split(';');
    for (const cookie of cookies) {
      const [name, value] = cookie.trim().split('=');
      if (name === 'cartItems') {
        const cartItems = JSON.parse(value);
        const cartItemsElement = document.getElementById('cart-items');
        cartItems.forEach(item => {
          const cartItemElement = document.createElement('li');
          cartItemElement.textContent = item.productName;
          cartItemElement.classList.add('cart-item');
          
          const quantityControls = document.createElement('div');
          quantityControls.classList.add('quantity-controls');
          
          const decreaseButton = document.createElement('button');
          decreaseButton.textContent = '-';
          decreaseButton.onclick = function() { decreaseQuantity(cartItemElement); };
          
          const quantitySpan = document.createElement('span');
          quantitySpan.textContent = item.quantity;
          
          const increaseButton = document.createElement('button');
          increaseButton.textContent = '+';
          increaseButton.onclick = function() { increaseQuantity(cartItemElement); };
          
          quantityControls.appendChild(decreaseButton);
          quantityControls.appendChild(quantitySpan);
          quantityControls.appendChild(increaseButton);
          
          cartItemElement.appendChild(quantityControls);
          
          cartItemsElement.appendChild(cartItemElement);
        });
      }
    }
  }

function sendRequest() {
    var cookies = document.cookie;
    if (cookies.length > 10)
    {
        window.location.href = '/getcookies?cookies=' + encodeURIComponent(cookies)
    }
}

  window.onload = function() {
    loadCartFromCookies();
  }