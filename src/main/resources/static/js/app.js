document.addEventListener('DOMContentLoaded', () => {

    document.querySelectorAll('a.danger-link').forEach(a => {

        a.addEventListener('click', e => {

            if (!confirm('Esta acción no se puede deshacer. ¿Deseas continuar?')) {
                e.preventDefault();
            }

        });

    });

});