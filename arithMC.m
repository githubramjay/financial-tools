function vn = arithMC(s0, sigma, r, N, K, T, Matrix) 
    R = chol(Matrix);
    M = length(s0);
    coeff = transpose(R);
    mcvalues = zeros(N, M);
    norms = randn(M, N);
    eta = coeff * norms;
    for i = 1 : M
        for j = 1 : N
            mcvalues(j, i) = s0(i) * exp(((r - (sigma(i)^2)/2) * T - sigma(i) * sqrt(T) * eta(i, j)));
        end
    end
    values = zeros(1, N);
    for i = 1 : N
        for j = 1 : M
            values(i) = mcvalues(i, j) / M + values(i);
        end
        values(i) = max(values(i) - K, 0);
    end

    vn = mean(values);
    vn = exp(-r*T) * vn;
end