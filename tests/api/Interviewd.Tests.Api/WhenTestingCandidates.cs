using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using DeepEqual.Syntax;
using Interviewd.Application.Dto;
using Interviewd.Domain.Model;
using Interviewd.Infrastructure.Abstraction;
using Jmansar.SemanticComparisonExtensions;
using Microsoft.Extensions.DependencyInjection;
using NUnit.Framework;
using Ploeh.AutoFixture;

namespace Interviewd.Tests.Api
{
    [TestFixture]
    public class WhenTestingCandidates : WhenTesting
    {
        [Test]
        public async Task ShouldBeAbleToCreateACandidate()
        {
            var requestCandidate = Fixture.Build<CandidateDto>()
                .Without(o => o.Id)
                .Create();

            var httpResponseMessage = await ApiClient.PostCandidate(requestCandidate);

            var responseCandidate = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<CandidateDto>();

            responseCandidate.ShouldEqual(requestCandidate);
        }

        [Test]
        public async Task ShouldBeAbleToGetAllCandidates()
        {
            var dbCandidates = await Arranger.CreateCandidates();

            var httpResponseMessage = await ApiClient.GetAllCandidates();

            var responseCandidateDtos = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetContent<IEnumerable<CandidateDto>>();

            var responseCandidates = Mapper.Map<IEnumerable<Candidate>>(responseCandidateDtos);

            responseCandidates = responseCandidates.Where(rc => dbCandidates.Any(dc => dc.Id == rc.Id));

            Assert.IsTrue(responseCandidates.CompareCollectionsUsingLikeness(dbCandidates));
        }

        [Test]
        public async Task ShouldBeAbleToGetCandidate()
        {
            var dbCandidate = await Arranger.CreateCandidate();

            var httpResponseMessage = await ApiClient.GetCandidate(dbCandidate.Id);

            var responseCandidateDto = await httpResponseMessage
                .EnsureSuccessStatusCode()
                .GetLikenessContent<CandidateDto>();

            responseCandidateDto.ShouldEqual(Mapper.Map<CandidateDto>(dbCandidate));
        }
    }
}