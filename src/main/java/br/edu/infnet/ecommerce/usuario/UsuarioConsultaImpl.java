package br.edu.infnet.ecommerce.usuario;

import br.edu.infnet.ecommerce.pagamento.domain.UsuarioConsulta;
import br.edu.infnet.ecommerce.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConsultaImpl implements UsuarioConsulta {

    private final UsuarioRepository usuarioRepository;

    public UsuarioConsultaImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean existeUsuario(Long usuarioId) {
        return usuarioRepository.existsById(usuarioId);
    }
}
