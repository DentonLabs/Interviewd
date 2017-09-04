﻿using System.Threading.Tasks;
using Interviewd.Application.Dto;

namespace Interviewd.Application
{
    public interface ICandidateManager
    {
        Task<CandidateDto> CreateCandidate(CandidateDto candidateDto);
    }
}