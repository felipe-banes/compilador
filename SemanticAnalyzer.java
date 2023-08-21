import ast.*;

public class SemanticAnalyzer {
    private SymbolTable symbolTable;

    public SemanticAnalyzer(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void analyze(Program program) {
        // Verificar se todas as variáveis foram inicializadas
        for (Declaration decl : program.getDeclarations()) {
            for (Identifier id : decl.getIdentifiers()) {
                if (!id.isInitialized()) {
                    throw new RuntimeException("Erro Semântico - Variável não inicializada: " + id.getName());
                }
            }
        }

        // Verificar se todas as variáveis usadas foram previamente declaradas
        for (AbstractCommand cmd : program.getCommands()) {
            if (cmd instanceof CmdAttrib) {
                CmdAttrib cmdAttrib = (CmdAttrib) cmd;
                Identifier id = cmdAttrib.getIdentifier();
                if (!symbolTable.exists(id.getName())) {
                    throw new RuntimeException("Erro Semântico - Variável não declarada: " + id.getName());
                }
            }
        }

        // Verificar se todas as variáveis declaradas foram usadas
        for (Declaration decl : program.getDeclarations()) {
            for (Identifier id : decl.getIdentifiers()) {
                if (!id.isUsed()) {
                    System.out.println("Aviso - Variável declarada não utilizada: " + id.getName());
                }
            }
        }
    }
}