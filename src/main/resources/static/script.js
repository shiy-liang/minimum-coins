document.getElementById("coin-calculator").addEventListener("submit",
    async function(e) {
        e.preventDefault();

        document.getElementById("amount-error").textContent = "";
        document.getElementById("denominations-error").textContent = "";
        document.getElementById("result").textContent = "";

        const amount = parseFloat(document.getElementById("amount").value);
        const denominations = document.getElementById("denominations").value
            .split(',')
            .map(x => parseFloat(x.trim()));

        if (isNaN(amount)) {
            document.getElementById('amount-error').textContent = 'Please enter amount';
            return;
        }
        if (denominations.some(d => isNaN(d))) {
            document.getElementById('denominations-error').textContent = 'Please enter denominations';
            return;
        }

        try{
            const postReq = await fetch('/api/input', {
                method: "POST",
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({amount, denominations})
            });
            if (!postReq.ok) {
                const errorData = await postReq.json();
                if (errorData.errors) {
                    if (errorData.errors.amount) {
                        document.getElementById('amount-error').textContent = errorData.errors.amount;
                    }
                    if (errorData.errors.denominations) {
                        document.getElementById('denominations-error').textContent = errorData.errors.denominations;
                    }
                } else if (errorData.message) {
                        alert(errorData.message);
                    }
                    return;
            }
            alert("submit successfully")
        const respond = await fetch("/api/calculator");
        const getData = await respond.json();
        console.log(getData);
        getData.sort((data1,data2)=> data1.denomination-data2.denomination)
        let displayText = "";
        for (const data of getData) {
            for (let i = 1; i <= data.count; i++) {
                displayText += `${data.denomination} `;
            }
        }
        document.getElementById("result").textContent = displayText;
        }catch (error) {
            alert("submit failed (exception): " + error.message);
        }
    });






