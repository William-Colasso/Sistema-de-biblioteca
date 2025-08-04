document.getElementById("fotoAutor").addEventListener("change", function () {
        const file = this.files[0];
        const reader = new FileReader();

        reader.onload = function (e) {
          const preview = document.getElementById("previewAutor");
          preview.src = e.target.result;
          preview.style.display = "block";
          preview.setAttribute("data-base64", e.target.result.split(",")[1]); // Remove header
        };

        if (file) reader.readAsDataURL(file);
      });

      document.getElementById("formAutor").addEventListener("submit", async function (e) {
        e.preventDefault();

        const fotoBase64 = document
          .getElementById("previewAutor")
          .getAttribute("data-base64") || "";

        const autor = {
          nomeAutor: document.getElementById("nomeAutor").value,
          fotoAutor: fotoBase64,
        };

        try {
          const response = await fetch("/autor/save", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(autor),
          });

          if (response.ok) {
            alert("Autor cadastrado com sucesso!");
            document.getElementById("formAutor").reset();
            document.getElementById("previewAutor").style.display = "none";
          } else {
            alert("Erro ao cadastrar o autor.");
          }
        } catch (error) {
          alert("Erro na requisição.");
          console.error(error);
        }
      });