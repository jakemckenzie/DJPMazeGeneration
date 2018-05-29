module Multiplexer#(parameter WIDTH,
                    parameter SELECT_WIDTH)
                    (input logic [WIDTH - 1 : 0]data_in[2**SELECT_WIDTH],
                     input logic [SELECT_WIDTH - 1 : 0]index,
                    output logic [WIDTH - 1 : 0]data_out);
        assign data_out = data_in[index];
endmodule
// module Multiplexer_testbench();
//     parameter WIDTH = 5, SELECT_WIDTH = 2;
//     logic [WIDTH - 1 : 0]data_in[2**SELECT_WIDTH];
//     logic [SELECT_WIDTH - 1 : 0]index
//     logic [WIDTH - 1 : 0]data_out;

//     Multiplexer #(WIDTH, SELECT_WIDTH) DUT(data_in, index, data_out);
    
    
// endmodule